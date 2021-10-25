import lexico.NossoScanner;
import lexico.Token;
import sintatico.Parser;

public class Principal {
  public static void main(String[] args) {
    NossoScanner nS = new NossoScanner("programa.xeque");
    Parser parser = new Parser(nS);

    parser.expressao();

    System.out.println("Compilador finalizado!");
    //Token token = null;
    
    /*do {
      token = nS.lerToken();
      if (token != null) {
        System.out.println(token.mostrarInfo());
      }
    } while (token != null);*/
  }
}