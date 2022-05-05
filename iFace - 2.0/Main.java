import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

class Main extends FuncoesMain {
    public static void main(String[] args) {
        
        //Variaveis globais
        ArrayList<Usuario> lista_usuarios = new ArrayList<Usuario>();
        ArrayList<Comunidade> lista_comunidades = new ArrayList<Comunidade>();
        Usuario usuarioAtual = null;

        //Inputs
        Scanner scanner = new Scanner(System.in);

        while(true){

            if(usuarioAtual == null){

                menuDeslogado();

                Integer opcaoUsuario = scanner.nextInt();

                if(opcaoUsuario == 1){ //Fazer conta

                    //Recebendo dados
                    String nome, sexo, nickname, password;
                    Integer idade;

                    nome = System.console().readLine("Informe seu nome: ");
                    sexo = System.console().readLine("Informe seu sexo (M, F, O): ");
                    System.out.print("Informe sua idade: ");
                    idade = scanner.nextInt();
                    nickname = System.console().readLine("Informe seu nickname: ");
                    password = System.console().readLine("Informe sua senha: ");

                    if(!buscaUsuario(nickname, lista_usuarios)){ //True: não existe usuario

                        System.out.println("Ja existe usuario com esse nickname. Por favor, escolher outro nome");

                    } else {

                        System.out.println("Usuario criado. Faca login para acessar sua nova conta.");
                        Usuario novoUsuario = new Usuario(nome, sexo, idade, nickname, password);
                        lista_usuarios.add(novoUsuario);

                    }

                } else if(opcaoUsuario == 2){ //Fazer login

                    String nickname, password;

                    nickname = System.console().readLine("Informe o nickname: ");
                    password = System.console().readLine("Informe a senha: ");

                    usuarioAtual = fazLogin(nickname, password, lista_usuarios);

                } else { //Sair do sistema

                    System.out.println("Saindo do sistema.");
                    break;

                }


            } else {

                menuLogado();

                Integer opcaoUsuario = scanner.nextInt();

                //1. Usuário; 2. Amigos; 3. Comunidades; 4. Mensagem; 5. Pesquisa (Usuário e Comunidade); 6. Deslogar;

                if(opcaoUsuario == 1){ //Configurações do usuário

                    menuOpcaoUsuario();

                    opcaoUsuario = scanner.nextInt();

                    if(opcaoUsuario == 1) { //Criar/Editar perfil

                        String endereco = System.console().readLine("Informe seu endereco: ");
                        String ocupacao = System.console().readLine("Informe sua ocupacao: ");

                        if(usuarioAtual.perfil == null){
                            
                            usuarioAtual.perfil = new Perfil(endereco, ocupacao);
                        
                        } else {

                            usuarioAtual.perfil.alteraPerfil(endereco, ocupacao);

                        }

                    } else if(opcaoUsuario == 2){//Apagar conta

                        deletarContaUsuario(lista_usuarios, lista_comunidades, usuarioAtual);
                        usuarioAtual = null;

                    } else if(opcaoUsuario == 3){//Controle de feed

                        usuarioAtual.mudarVisibilidadeFeed();

                    } else if(opcaoUsuario == 4){ //Mostrar informações

                        usuarioAtual.toString();

                    }

                } else if(opcaoUsuario == 2){ //Amigos
 
                    menuOpcaoAmigos();

                    opcaoUsuario = scanner.nextInt();

                    if(opcaoUsuario == 1){

                        String usuario = System.console().readLine("Informe o nome do usuario: ");

                        if(!buscaUsuario(usuario, lista_usuarios)){

                            //Pegando o obj
                            for(Usuario usuarioBuscado : lista_usuarios){

                                if(usuarioBuscado.nickname.equals(usuario)){

                                    usuarioBuscado.insereSolicitacaoAmizade(usuarioAtual);

                                }

                            }

                        }

                    } else if(opcaoUsuario == 2){ //Ver solicitações

                        usuarioAtual.verificaSolicitacoes();

                    }

                } else if(opcaoUsuario == 3){ //Comunidades

                    menuOpcaoComunidade();

                    opcaoUsuario = scanner.nextInt();

                    if(opcaoUsuario == 1){

                        String nome = System.console().readLine("Nome da comunidade: ");
                        String descricao = System.console().readLine("Descricao da comunidade: ");

                        if(buscaComunidade(nome, lista_comunidades)){

                            System.out.println("Nome de comunidade ja em uso. Use outro nome.");

                        } else {

                            System.out.println("Comunidade criada com sucesso.");
                            
                            Comunidade novaComunidade = new Comunidade(nome, descricao, usuarioAtual);
                            lista_comunidades.add(novaComunidade);

                        }

                    } else if(opcaoUsuario == 2){

                        String nome = System.console().readLine("Nome da comunidade: ");

                        if(buscaComunidade(nome, lista_comunidades)){

                            for(Comunidade comunidade : lista_comunidades){

                                if(comunidade.nome.equals(nome)){

                                    if(comunidade.membros.contains(usuarioAtual)){

                                        System.out.println("Voce ja faz parte da comunidade.");

                                    } else {

                                        comunidade.insereSolicitacoes(usuarioAtual);

                                    }

                                }

                            }

                        } else {

                            System.out.println("Comunidade encontrada.");
                        }

                    } else if(opcaoUsuario == 3){

                        String nome = System.console().readLine("Nome da comunidade: ");

                        if(buscaComunidade(nome, lista_comunidades)){

                            for(Comunidade comunidade : lista_comunidades){

                                if(comunidade.nome.equals(nome)){

                                    comunidade.verificaSolicitacoes(usuarioAtual);

                                }

                            }

                        } else {

                            System.out.println("Nao existe essa comunidade.");

                        }

                    }

                } else if(opcaoUsuario == 4){ //Mensagem

                    menuOpcaoMensagem();

                    opcaoUsuario = scanner.nextInt();

                    if(opcaoUsuario == 1){ //Para usuário

                        String mensagemEnviada = System.console().readLine("Digite a mensagem: ");
                        String usuarioNickname = System.console().readLine("Infome o nickname do usuario: ");

                        if(!buscaUsuario(usuarioNickname, lista_usuarios)){

                            for(Usuario usuario : lista_usuarios){

                                if(usuario.nickname.equals(usuarioNickname)){

                                    LocalDate data = LocalDate.now();
                                    Mensagem mensagem = new Mensagem(mensagemEnviada, usuarioAtual.nickname, data);

                                    usuario.enviaMensagem(mensagem);

                                }

                            }

                        } else {

                            System.out.println("Usuario nao encontrado.");

                        }

                    } else if(opcaoUsuario == 2){ //Para comunidade

                        String mensagemEnviada = System.console().readLine("Digite a mensagem: ");
                        String comunidade = System.console().readLine("Infome o nome da comunidade: ");

                        if(buscaComunidade(comunidade, lista_comunidades)){

                            for(Comunidade comunidade2 : lista_comunidades){

                                if(comunidade2.nome.equals(comunidade)){

                                    LocalDate data = LocalDate.now();
                                    Mensagem mensagem = new Mensagem(mensagemEnviada, usuarioAtual.nickname, data);

                                    comunidade2.enviaMensagem(mensagem);

                                }

                            }

                        } else {

                            System.out.println("Comunidade nao encontrada.");

                        }

                    } else if(opcaoUsuario == 3){ //Feed usuario

                        String nickname = System.console().readLine("Nickname do usuario: ");

                        if(!buscaUsuario(nickname, lista_usuarios)){

                            String mensagem = System.console().readLine("Mensagem do post: ");

                            for(Usuario usuario : lista_usuarios){

                                if(usuario.nickname.equals(nickname)){

                                    if(usuario.amigos.contains(usuarioAtual)){//Pode postar porque é amigo

                                        LocalDate data = LocalDate.now();
                                        Post post = new Post(mensagem, usuarioAtual.nickname, data);

                                        usuario.feed.add(post);

                                    }

                                }

                            }

                        }

                    } else if(opcaoUsuario == 4){ //Feed comunidade

                        String nome = System.console().readLine("Nome da comunidade: ");

                        if(buscaComunidade(nome, lista_comunidades)){

                            String mensagem = System.console().readLine("Mensagem do post: ");

                            for(Comunidade comunidade : lista_comunidades){

                                if(comunidade.nome.equals(nome)){

                                    if(comunidade.membros.contains(usuarioAtual)){//Pode postar porque é membro

                                        LocalDate data = LocalDate.now();
                                        Post post = new Post(mensagem, usuarioAtual.nickname, data);

                                        comunidade.feed.add(post);

                                    }

                                }

                            }

                        }

                    } else if(opcaoUsuario == 5){

                        usuarioAtual.mostraMensagens();

                    }

                } else if(opcaoUsuario == 5){ //Recuperar informações sobre um usuário

                    //Perfil, comunidades, amigos, feed
                    String usuario = System.console().readLine("Informe o nickname do usuario: ");

                    if(!buscaUsuario(usuario, lista_usuarios)){

                        for(Usuario buscado : lista_usuarios){

                            if(buscado.nickname.equals(usuario)){

                                //Verificar se são amigos ou se o feed é público
                                System.out.println(buscado.toString());

                                if(buscado.perfil != null) {

                                    System.out.println(buscado.perfil.toString());

                                } else {

                                    System.out.println("Nao ha perfil desse usuario.");

                                }
                                

                                if(buscado.perfilPublico){

                                    buscado.mostraFeed(buscado);
                                    
                                } else if(!buscado.perfilPublico && buscado.amigos.contains(usuarioAtual)){

                                    buscado.mostraFeed(buscado);

                                } else {

                                    System.out.println("Nao é possível visualizar o feed de " + buscado.nickname);

                                }

                            }

                        }

                    } else {

                        System.out.println("Esse usuario nao existe.");

                    }

                } else if(opcaoUsuario == 6){

                    System.out.println("Voce sera deslogado em instantes.");
                    usuarioAtual = null;

                } else {

                    System.out.println("Digite uma opção valida.");

                }

            }

        }

    }

}