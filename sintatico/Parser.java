package sintatico;

import lexico.NossoScanner;
import lexico.Token;

public class Parser {
  private NossoScanner nS;
  private Token token;

  public Parser(NossoScanner nS) {
    this.nS = nS;
  }

  public void expressao() {
    // x + y, a + b, a
    termo();
    expressaoLinha();
  }

  public void expressaoLinha() {
    this.token = nS.lerToken();

    if (token != null) {
      operador();
      termo();
      expressaoLinha();
    }
  }

  public void termo() {
    this.token = nS.lerToken();

    if (this.token == null) {
      throw new RuntimeException("Erro de sintaxe, aguardava um identificador ou número.");
    }
    
    if (this.token.getTipo() != "identificador" && this.token.getTipo() != "número") {
      throw new RuntimeException("Erro de sintaxe, aguardava um identificador ou número.");
    }
  }

  public void operador() {
    if (this.token.getTipo() != "operador") {
      throw new RuntimeException("Erro de sintaxe, aguardava um operador.");
    }
  }
  
}