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
    
    private boolean[] decoficarDadoHemming(boolean bits[]){
        //Primeira parte seria verificar se houve perda de dados com o ruido
        boolean bitsCorretos;
        
        //Usando XOR para verificação se houve perda de dados
        //Começando sempre de baixo para cima
        String num="";
          bitsCorretos = bits[7] ^ bits[8] ^ bits[9] ^ bits[10] ^ bits[11];
          if( bitsCorretos){
            num += 1;
        }else{
            num += 0;
        }
         bitsCorretos= bits[3] ^bits[4] ^ bits[5] ^ bits[6] ^ bits[11];
          if( bitsCorretos){
            num += 1;
        }else{
            num += 0;
        }
        bitsCorretos = bits[1] ^bits[2] ^ bits[5] ^ bits[6] ^ bits[9] ^ bits[10];
        if( bitsCorretos){
            num += 1;
        }else{
            num += 0;
        }
        bitsCorretos = bits[0] ^ bits[2] ^ bits[4] ^ bits[6] ^ bits[8] ^ bits[10];
        if( bitsCorretos){
            num += 1;
        }else{
            num += 0;
        }
           
        //Transformando a casa de binario para decimal usando ParseInt
        int erro = Integer.parseInt(num, 2);
           
        //Se for 0 não houve perda de dados, se não for o número resposta é a casa onde houve o ruido e precisa ser corrigido
        if(erro != 0){
            bits[erro-1] = !bits[erro-1];
        }
        //Variavel auxiliar usada para colocar os 12 bits de Hamming para 8 bits de dado
        int intAdicional=0;
        
        //Bits que serão enviados para serem decodificados
        boolean newBits[] = new boolean[8];
        
        //Colocando os 8 bits de dados em um vetor somente com eles, para decodificação
         for(int x = 0; x!= bits.length; x++){
            if(x != 0 && x != 1 && x != 3 && x != 7){
                newBits[intAdicional] = bits[x];
                intAdicional++;
            }   
        }
         
         //Retornando os novos bits se houve perda de dados
        return newBits;
        
    }
    
    
    //recebe os dados do transmissor
    public void receberDadoBits(boolean bits[]){
        
        bits = decoficarDadoHemming(bits);
        
        decodificarDado(bits);
    }
}
