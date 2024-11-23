package buffet.app_web.util;

public class PilhaObj<T> {

    private int topo;
    private T[] pilha;

    public PilhaObj(int capacidade){
        this.topo = -1;
        this.pilha = (T[])new Object[capacidade];
    }

    public boolean isEmpty(){
        return topo == -1;
    }

    public boolean isFull(){
        return topo == pilha.length - 1;
    }

    public void push(T info){
        if (isFull()){
            throw new IllegalStateException();
        }
        pilha[++topo] = info;
    }

    public T pop(){
        if (!isEmpty()){
            return pilha[topo--];
        }

        return null;
    }

    public T peek(){
        if (!isEmpty()){
            return pilha[topo];
        }

        return null;
    }

    public void exibe(){
        for (int i = topo; i >= 0 ; i--) {
            System.out.println(pilha[i]);
        }
    }


    //Getters & Setters (manter)
    public int getTopo() {
        return topo;
    }

    public T[] getPilha(){
        return pilha;
    }
}