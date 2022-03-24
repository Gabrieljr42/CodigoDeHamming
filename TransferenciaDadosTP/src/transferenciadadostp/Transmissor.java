package transferenciadadostp;

import java.util.Random;

public class Transmissor {
    private String mensagem;

    public Transmissor(String mensagem) {
        this.mensagem = mensagem;
    }
    
    //convertendo um símbolo para "vetor" de boolean (bits)
    private boolean[] streamCaracter(char simbolo){
        
        //cada símbolo da tabela ASCII é representado com 8 bits
        boolean bits[] = new boolean[8];
        
        //convertendo um char para int (encontramos o valor do mesmo na tabela ASCII)
        int valorSimbolo = (int) simbolo;
        int indice = 7;
        
        //convertendo cada "bits" do valor da tabela ASCII
        while(valorSimbolo >= 2){
            int resto = valorSimbolo % 2;
            valorSimbolo /= 2;
            bits[indice] = (resto == 1);
            indice--;
        }
        bits[indice] = (valorSimbolo == 1);
        
        return bits;
    } 
    
    //não modifique (seu objetivo é corrigir esse erro gerado no receptor)
    private void geradorRuido(boolean bits[]){
        Random geradorAleatorio = new Random();
        
        //pode gerar um erro ou não..
        if(geradorAleatorio.nextInt(5) > 1){
            int indice = geradorAleatorio.nextInt(8);
            bits[indice] = !bits[indice];
        }
    }
    
    private boolean[] dadoBitsHemming(boolean bits[]){
        //Criando o vetor com 12 casas, 8 com dados e 4 com o código de Hamming
        boolean bitsHemming[] = new boolean[12];
        
       //Variavel auxiliar usada para colocar os 8 bits de dado para 12 bits de Hamming
        int intAdicional=0;
        
        for(int x = 0; x!= bitsHemming.length; x++){
            //Como já sabemos as casas que serão usadas para o código, já separamos elas( tirando -1 devido o vetor começar em 0)
            if(x != 0 && x != 1 && x != 3 && x != 7){
                bitsHemming[x] = bits[intAdicional];
                intAdicional++;
            }   
        }
        
       //Fazendo o XOR para achar os bits de Hamming(Sempre lembranco que começa no 0 e vai até o 11)
       //0 -> 2,4,6,8,10
       //1 -> 2,5,6,9,10
       //3 -> 4,5,6,11
       //7 -> 8,9,10,11
        bitsHemming[0] = bitsHemming[2] ^ bitsHemming[4] ^ bitsHemming[6] ^ bitsHemming[8] ^ bitsHemming[10];
        bitsHemming[1] = bitsHemming[2] ^ bitsHemming[5] ^ bitsHemming[6] ^ bitsHemming[9] ^ bitsHemming[10];
        bitsHemming[3] = bitsHemming[4] ^ bitsHemming[5] ^ bitsHemming[6] ^ bitsHemming[11];
        bitsHemming[7] = bitsHemming[8] ^ bitsHemming[9] ^ bitsHemming[10] ^ bitsHemming[11];
      
        return bitsHemming;
    }
    
    public void enviaDado(Receptor receptor){
        for(int i = 0; i < this.mensagem.length();i++){
            boolean bits[] = streamCaracter(this.mensagem.charAt(i));
            
            //Transformando os dados de bits normais para bitsDeHamming.
            boolean bitsHemming[] = dadoBitsHemming(bits);
            
            
            //add ruidos na mensagem a ser enviada para o receptor
            geradorRuido(bitsHemming);
            
            //enviando a mensagem "pela rede" para o receptor
            receptor.receberDadoBits(bitsHemming);
        }
    }
}
