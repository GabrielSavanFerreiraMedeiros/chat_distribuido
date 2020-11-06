
    class Mensagem {
    private String mensagem;
    private Usuario remetente;
    private Usuario destinatario;

    public Mensagem( String mensagem, Usuario remetente, Usuario destinatario ){
        this.mensagem = mensagem;
        this.remetente = remetente;
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }
}
