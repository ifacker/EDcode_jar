package plugins.EDcode.Util.ED;

import java.util.Arrays;

public class Vigenere {
    private static char[][] table;

    public static String encode(String message, String key)
    {
        vigenereTable();

        String keyUpcaseNoSpace = key.toUpperCase().replaceAll("[^a-zA-Z]+", "");
        String messageUpcaseNoSpace = message.toUpperCase().replaceAll("[^a-zA-Z]+", "");

        // passkey String turned into char array
        char[] keyArray = keyUpcaseNoSpace.toCharArray();

        // message String turned into char array
        char[] messageArray = messageUpcaseNoSpace.toCharArray();

        String result = "";

        for(int i = 0; i < messageArray.length; i++)
        {
            //assign each keyChar char to variable and increment
            char keyChar = keyArray[i%keyArray.length];

            //assign each messageChar char to variable and increment
            char messageChar = messageArray[i%messageArray.length];

            //cast char to int
            int keyCharPosition = (int)keyChar;

            //cast char to int
            int messageCharPosition = (int)messageChar;
//            System.out.print(table[keyCharPosition-65][messageCharPosition-65]);
            result += table[keyCharPosition-65][messageCharPosition-65];
        }//end for loop
        return result;
    }//end encrypt method

    public static String decode(String encryptedMessage, String key)
    {
        vigenereTable();

        String keyUpcaseNoSpace = key.toUpperCase().replaceAll("[^a-zA-Z]+", "");
        String encryptedMessageUpcaseNoSpace = encryptedMessage.toUpperCase().replaceAll("[^a-zA-Z]+", "");

        // passkey String turned into char array
        char[] keyArray = keyUpcaseNoSpace.toCharArray();

        // message String turned into char array
        char[] encryptedMessageArray = encryptedMessageUpcaseNoSpace.toCharArray();

        String result = "";

        for(int i = 0; i < encryptedMessageArray.length; i++)
        {
            //assign each keyChar char to variable and increment
            char keyChar = keyArray[i%keyArray.length];

            //assign each messageChar char to variable and increment
            char encryptedMessageChar = encryptedMessageArray[i%encryptedMessageArray.length];

            //cast char to int
            int keyCharPosition = (int)keyChar;

            for( int j = 0; j < table.length; j++)
            {
                if(Arrays.asList(table[j][keyCharPosition-65]).contains(encryptedMessageChar))
                {
//                    System.out.print(table[j][0]);
                    result += table[j][0];
                }
            }//end for loop
        }//end for loop
        return result;
    }//end decrypt method
    private static void vigenereTable()
    {
        int ROWS = 26;
        int COLS = 26;
        // 2d alphabet char array as a table to encrypt message
        table = new char[ROWS][COLS];

        // for loops to generate the table
        for(int i = 0; i < 26; i++)
        {
            for(int j = 0; j < 26; j++)
            {
                // creates an int that will be cast into a char
                int letter = i + j;

                // stops int from going above 26
                if (letter >= 26)
                {
                    letter = letter - 26;
                }
                // Upper case alphabet in char list starts at 65 mindprod.com/jgloss/ascii.html#ASCIITABLE
                letter = letter + 65;

                // cast int to char
                table[i][j] = (char)letter;
            }//end for loop
        }//end for loop
    }
}
