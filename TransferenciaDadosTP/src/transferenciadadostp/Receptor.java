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
        boolean[] bitsCorretos = new boolean[12];
        String num="";
        bitsCorretos[0] = bits[0] ^ bits[2] ^ bits[4] ^ bits[6] ^ bits[8] ^ bits[10];
        if( bitsCorretos[0]){
            num += 1;
        }else{
            num += 0;
        }
        bitsCorretos[1] = bits[1] ^bits[2] ^ bits[5] ^ bits[6] ^ bits[9] ^ bits[10];
        if( bitsCorretos[1]){
            num += 1;
        }else{
            num += 0;
        }
        bitsCorretos[3] = bits[3] ^bits[4] ^ bits[5] ^ bits[6] ^ bits[11];
          if( bitsCorretos[3]){
            num += 1;
        }else{
            num += 0;
        }
        bitsCorretos[7] = bits[7] ^bits[8] ^ bits[9] ^ bits[10] ^ bits[11];
          if( bitsCorretos[7]){
            num += 1;
        }else{
            num += 0;
        }
        int resul=0;
        System.out.println(num);
        for(int x = 0; x!= num.length();x++){
            if(num.substring(x,x+1)== "1"){
                resul += Math.pow(2, Integer.parseInt(num.substring(x,x+1)));
            }
        }
        if(resul != 0){
            bits[resul] = !bits[resul];
        }
        int intAdicional=0;
        boolean newBits[] = new boolean[8];
         for(int x = 0; x!= bits.length; x++){
            if(x != 0 && x != 1 && x != 4 && x != 8){
                newBits[intAdicional] = bits[x];
                intAdicional++;
            }   
        }
        return newBits;
        
    }
    
    
    //recebe os dados do transmissor
    public void receberDadoBits(boolean bits[]){
        
        //aqui você deve trocar o médodo decofificarDado para decoficarDadoHemming (implemente!!)
        bits = decoficarDadoHemming(bits);
        decodificarDado(bits);
    }
}
