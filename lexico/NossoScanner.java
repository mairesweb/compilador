package lexico;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NossoScanner {
  private char [] conteudoArray;
  private int posicao;
  private int estado;
  
  public NossoScanner(String nomeArquivo) {
    try {
      // pegar o conteudo do arquivo
      String conteudoArquivo = new String(Files.readAllBytes(Paths.get(nomeArquivo)), StandardCharsets.UTF_8);

      // transformar o conteudo em um array de caracteres
      this.conteudoArray = conteudoArquivo.toCharArray();
      posicao = 0;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Token lerToken() {
    char caractereLido;
    Token token;
    String lexema = "";

    if (ehEOF()) {
      return null;
    }

    this.estado = 0;
    while(true) {
      caractereLido = proximoCaractere();

      switch(this.estado) {
        case 0:
          if (ehCaractere(caractereLido)) { // identificador ou palavra reservada
            this.estado = 1;
            lexema += caractereLido;
          } else if (ehDigito(caractereLido)) { // número
            this.estado = 2;
            lexema += caractereLido;
          } else if (ehEspaco(caractereLido)) { // espaço
            this.estado = 0;
          } else if (ehOperador(caractereLido)) { // operador
            this.estado = 3;
            lexema += caractereLido;
          } else {
            throw new RuntimeException("Simbolo não reconhecido " + caractereLido);
          }

          break;
        case 1:
          if (ehCaractere(caractereLido) || ehDigito(caractereLido)) {
            this.estado = 1;
            lexema += caractereLido;
          } else if (ehOperador(caractereLido) || ehEspaco(caractereLido) || ehEOF(caractereLido)) {
            if (!ehEOF(caractereLido)) {
              this.voltarCaractere();
            }

            token = new Token();
            token.setTipo("identificador");
            token.setValor(lexema);
            return token;
          } else {
            throw new RuntimeException("Simbolo não reconhecido " + caractereLido);
          }

          break;

        case 2:
          if (ehDigito(caractereLido) || caractereLido == '.') {
            this.estado = 2;
            lexema += caractereLido;
          } else if (
            !ehCaractere(caractereLido) ||
            ehEOF(caractereLido)) {
              if (!ehEOF(caractereLido)) {
                this.voltarCaractere();
              }

              token = new Token();
              token.setTipo("número");
              token.setValor(lexema);

              return token;
          } else {
            throw new RuntimeException("Simbolo não reconhecido " + caractereLido);
          }
          break;
        case 3:
          if (ehOperador(caractereLido)) {
            this.estado = 4;
            lexema += caractereLido;
          } else if (
            ehCaractere(caractereLido) ||
            ehEspaco(caractereLido) ||
            ehDigito(caractereLido) ||
            ehEOF(caractereLido)
          ) {
            if (!ehEOF(caractereLido)) {
              this.voltarCaractere();
            }

            this.estado = 0;

            token = new Token();
            token.setTipo("operador");
            token.setValor(lexema);
            return token;
          } else {
            throw new RuntimeException("Simbolo não reconhecido " + caractereLido);
          }

          break;
        case 4:
          if (
            ehCaractere(caractereLido) ||
            ehEspaco(caractereLido) ||
            ehDigito(caractereLido) ||
            ehEOF(caractereLido)
          ) {
            if (!ehEOF(caractereLido)) {
              this.voltarCaractere();
            }

            this.estado = 0;

            token = new Token();
            token.setTipo("operador");
            token.setValor(lexema);
            return token;
          } else {
            lexema += caractereLido;
            throw new RuntimeException("Simbolo não reconhecido " + lexema);
          }
      }
    }
  }

  private boolean ehCaractere(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  private boolean ehDigito(char c) {
    return (c >= '0' && c <= '9') || c == '.';
  }

  private boolean ehOperador(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/' || c == '=';
  }

  private boolean ehEspaco(char c) {
    return c == ' ' || c == '\t' || c == '\n' || c == '\r';
  }

  private boolean ehEOF(char c) {
    return c == '\0';
  }

  private boolean ehEOF() {
    return this.posicao >= this.conteudoArray.length;
  }

  private char proximoCaractere() {
    if (this.ehEOF()) {
      return '\0';
    }
    
    return this.conteudoArray[posicao++];
  }

  private void voltarCaractere() {
    this.posicao--;
  }

  private boolean ehPalavraReservada(char c) {
    int temp = this.posicao;
    if (c == 'c') {
      if (this.conteudoArray[temp++] == 'l') {
        if (this.conteudoArray[temp++] == 'a') {
          if (this.conteudoArray[temp++] == 's') {
            if (this.conteudoArray[temp++] == 's') {
              this.posicao = temp;
              return true;
            }
          }
        }
      }
    } else if (c == 'f') {
      
    }

    return false;
  }
}