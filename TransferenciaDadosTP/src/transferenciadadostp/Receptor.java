package transferenciadadostp;

public class Receptor {
    
    //mensagem recebida pelo transmissor
    private String mensagem;

    public Receptor() {
        //mensagem vazia no inicio da execução
        this.mensagem = "";
    }
    
    public String getMensagem() {
        return mensagem;
    }
 
    private void decodificarDado(boolean bits[]){
        int codigoAscii = 0;
        int expoente = bits.length-1;
        
        //converntendo os "bits" para valor inteiro para então encontrar o valor tabela ASCII
        for(int i = 0; i < bits.length;i++){
            if(bits[i]){
                codigoAscii += Math.pow(2, expoente);
            }
            expoente--;
        }
        
        //concatenando cada simbolo na mensagem original
        this.mensagem += (char)codigoAscii;
    }
    
    private void decoficarDadoHemming(){
        
        //implemente a decodificação Hemming aqui e encontre os 
        //erros e faça as devidas correções para ter a imagem correta
    }
    
    
    //recebe os dados do transmissor
    public void receberDadoBits(boolean bits[]){
        
        //aqui você deve trocar o médodo decofificarDado para decoficarDadoHemming (implemente!!)
        decodificarDado(bits);
    }
}
