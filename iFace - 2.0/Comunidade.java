import java.util.ArrayList;
import java.util.Scanner;

public class Comunidade {
    
    public String nome;
    public String descricao;
    public Usuario administrador;

    //Listas
    public ArrayList<Usuario> membros = new ArrayList<Usuario>();
    private ArrayList<Usuario> solicitacoes = new ArrayList<Usuario>();
    private ArrayList<Mensagem> mensagens = new ArrayList<Mensagem>();
    public ArrayList<Post> feed = new ArrayList<Post>();

    public Comunidade(String nome, String descricao, Usuario administrador){

        this.nome = nome;
        this.descricao = descricao;
        this.administrador = administrador;

        administrador.comunidades.add(this);

    }

    public void enviaMensagem(Mensagem mensagem){

        this.mensagens.add(mensagem);

    }

    public void verificaSolicitacoes(Usuario usuario){

        if(usuario.equals(this.administrador)){

            for(Usuario solicitacao : this.solicitacoes){

                System.out.println(solicitacao.nickname + " quer entrar na comunidade. 1: SIM; 0: NAO.");
    
                Scanner scanner = new Scanner(System.in);
                Integer opcaoUsuario = scanner.nextInt();
    
                if(opcaoUsuario == 1){

                    System.out.println("Usuario adicionado.");
    
                    this.membros.add(solicitacao);
                    solicitacao.comunidades.add(this);

                } else {

                    System.out.println("Usuario nao aceito na comunidade.");
                    this.solicitacoes.remove(solicitacao);

                }
    
            }

        } else {

            System.out.println("Somente o administrador pode fazer isso.");

        }

    }

    public void insereSolicitacoes(Usuario usuario){

        this.solicitacoes.add(usuario);

    }

}
