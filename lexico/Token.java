package lexico;

public class Token {
  private String tipo;
  private String valor;

  public Token() {}
  public Token(String tipo, String valor) {
    this.tipo = tipo;
    this.valor = valor;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  public String mostrarInfo() {
    return "O tipo é " + this.tipo + " e o valor é " + this.valor;
  }
}
