package buffet.app_web.service;

import buffet.app_web.configuration.security.jwt.GerenciadorTokenJwt;
import buffet.app_web.entities.Buffet;
import buffet.app_web.entities.Usuario;
import buffet.app_web.mapper.UsuarioMapper;
import buffet.app_web.repositories.OrcamentoRepository;
import buffet.app_web.repositories.UsuarioRepository;
import buffet.app_web.service.autenticacao.dto.UsuarioLoginDto;
import buffet.app_web.service.autenticacao.dto.UsuarioTokenDto;
import buffet.app_web.strategies.UsuarioStrategy;
import buffet.app_web.util.email.ConstroiAssuntosEmail;
import buffet.app_web.util.email.ConstroiMensagensEmail;
import buffet.app_web.util.email.GeradorCodigoVerificacao;
import jakarta.mail.MessagingException;
import org.hibernate.dialect.function.ListaggStringAggEmulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService implements UsuarioStrategy {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OrcamentoRepository orcamentoRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BuffetService buffetService;

    public List<Usuario> listarTodos(){
        return  usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id){
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Usuario salvar(Usuario usuario, Long buffetId){
        Buffet buffet = buffetService.buscarPorId(buffetId);

        if (usuarioRepository.findByEmailAndBuffetId(usuario.getEmail(), buffetId).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        usuario.setBuffet(buffet);
        return  usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmailEBuffet(String email, Long buffetId) {
        return usuarioRepository.findByEmailAndBuffetId(email, buffetId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario atualizar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void deletar(Integer id){
        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto, Long buffetId) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                usuarioRepository
                        .findByEmailAndBuffetId(usuarioLoginDto.getEmail(), buffetId)
                        .orElseThrow(() -> new ResponseStatusException(404, "Email do usuário não cadastrado", null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);
        Integer qtdOrcamento = orcamentoRepository.countByUsuarioId(usuarioAutenticado.getId());

        return UsuarioMapper.of(usuarioAutenticado, token, qtdOrcamento);
    }


    public Usuario alterarSenha(String email, String novaSenha, String novaSenhaConfirmacao) {
        Usuario usuario = buscarPorEmail(email);

        if (!novaSenha.equals(novaSenhaConfirmacao)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "As senhas não são iguais");
        }

        String novaSenhaCriptografada = passwordEncoder.encode(novaSenha);
        usuario.setSenha(novaSenhaCriptografada);
        GeradorCodigoVerificacao.removerCodigo(email);

        return usuarioRepository.save(usuario);
    }

    public void enviarCodigo(String email) {
        buscarPorEmail(email);

        String codigo = GeradorCodigoVerificacao.gerarCodigoVerificacao();
        GeradorCodigoVerificacao.armazenarCodigo(email, codigo);
        try {
            emailService.enviarCodigoVerificacao(
                    email,
                    ConstroiAssuntosEmail.construirAssuntoCodigoVerficacao(),
                    ConstroiMensagensEmail.construirMensagemCodigo(codigo)
            );
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar e-mail HTML", e);
        }
    }

    public void validar(String email, String codigoInserido){
        buscarPorEmail(email);

        if (!validarCodigo(email, codigoInserido)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static boolean validarCodigo(String email, String codigoInserido) {
        String codigoArmazenado = GeradorCodigoVerificacao.getCodigo(email);
        return codigoArmazenado != null && codigoArmazenado.equals(codigoInserido);
    }

    public Usuario buscarPorEmail(String email){
        return usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
