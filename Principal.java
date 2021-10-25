import lexico.NossoScanner;
import lexico.Token;

public class Principal {
  public static void main(String[] args) {
    NossoScanner nS = new NossoScanner("programa.xeque");
    Token token = null;
    
    do {
      token = nS.lerToken();
      if (token != null) {
        System.out.println(token.mostrarInfo());
      }
    } while (token != null);
  }
}