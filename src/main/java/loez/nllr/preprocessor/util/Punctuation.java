/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loez.nllr.preprocessor.util;

/**
 *
 * @author ljleppan
 */
public class Punctuation implements PreprocessorUtil{
    
    @Override
    public String replace(String input, String replacement){
        return input.replaceAll("\\p{Punct}+", replacement);
    }
    
    @Override
    public String remove(String input){
        return replace(input, "");
    }
}
