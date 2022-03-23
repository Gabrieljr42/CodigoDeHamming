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
        boolean bitsHemming[] = new boolean[12];
        int bitsAdicionais = 4, intAdicional=0;
        for(int x = 0; x!= bitsHemming.length; x++){
            if(x != 0 && x != 1 && x != 4 && x != 8){
                //bitsHemming[x] = true;
                bitsHemming[x] = bits[intAdicional];
                intAdicional++;
            }   
        }
        //Setando os números nas casas;
        // Para a casa 0 :  3 -> 5 -> 7 -> 9 -> 11
        // Para a casa 2 :  3 -> 6 -> 7 ->10 -> 11
        // Para a casa 4 :  5 -> 6 -> 7 -> 12 
        // Para a casa 8 :  9 -> 10 -> 11 -> 12
        bitsHemming[0] = bitsHemming[2] ^ bitsHemming[4] ^ bitsHemming[6] ^ bitsHemming[8] ^ bitsHemming[10];
        bitsHemming[1] = bitsHemming[2] ^ bitsHemming[5] ^ bitsHemming[6] ^ bitsHemming[9] ^ bitsHemming[10];
        bitsHemming[3] = bitsHemming[4] ^ bitsHemming[5] ^ bitsHemming[6] ^ bitsHemming[11];
        bitsHemming[7] = bitsHemming[8] ^ bitsHemming[9] ^ bitsHemming[10] ^ bitsHemming[11];
        for(int x = 0; x!= bitsHemming.length;x++){
          
        }
        return bitsHemming;
    }
    
    public void enviaDado(Receptor receptor){
        for(int i = 0; i < this.mensagem.length();i++){
            boolean bits[] = streamCaracter(this.mensagem.charAt(i));
            
          
            boolean bitsHemming[] = dadoBitsHemming(bits);
            
            
            //add ruidos na mensagem a ser enviada para o receptor
            geradorRuido(bitsHemming);
            
            //enviando a mensagem "pela rede" para o receptor
            receptor.receberDadoBits(bitsHemming);
        }
    }
}
