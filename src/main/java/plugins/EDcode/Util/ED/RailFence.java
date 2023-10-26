package plugins.EDcode.Util.ED;

public class RailFence {
    public static String encode(String code, int key) {
        char[] plain = code.toCharArray();				//converts the string to char array
        char[][] cipher=new char[key][plain.length];	//declare the 2 dimentional array of characters where we store the plain text in zig zag form.

        //initialise array element to #
        for(int i=0;i<key;i++)
            for(int j=0;j<plain.length;j++)
                cipher[i][j]='#';

        int level=0;
        int flag=0;

        //traverse the cipher diagonally and insert the plain text into the cipher in zig zag form
        for(int i=0;i<plain.length;i++){
            cipher[level][i]=plain[i];

            //traverse the cipher diagonally downward until the flag is set to 0 and increment the level
            if(flag==0){
                level++;

                //once you reach the bottom level set flag to 1 to mark the end of first half of the cycle
                if(level==key-1)
                    flag=1;
            }

            //traverse the cipher diagonally upwards until the flag is set to 1 and decrement the level
            else{
                level--;

                //once you reach the top level set the flag to 0 to mark the end of the cycle
                if(level==0)
                    flag=0;
            }
        }

        String cipher_text="";


        //Read the 2 dimentional cipher, level wise and store it into the cipher_text variable.
        for(int i=0;i<key;i++){
            for(int j=0;j<plain.length;j++){
                if(cipher[i][j]!='#')
                    cipher_text+=cipher[i][j];
            }
        }
        return cipher_text;
    }

    public static String decode(String code, int key) {
        char[] plain = code.toCharArray();				//converts the string to char array
        char[][] cipher=new char[key][plain.length];	//declare the 2 dimentional array of characters where we store the plain text in zig zag form.

        //initialise array element to #
        for(int i=0;i<key;i++)
            for(int j=0;j<plain.length;j++)
                cipher[i][j]='#';

        int level=0;
        int flag=0;
        int index=0;

        //traverse the cipher in zig zag form and insert '*'
        for(int i=0;i<plain.length;i++){
            cipher[level][i]='*';

            //traverse the 2 dimentional array diagonally downwards until flag is set to 0 and increment the level
            if(flag==0){
                level++;

                //once you reach the bottom level set flag to 1 to mark the end of first half of the cycle
                if(level==key-1)
                    flag=1;
            }

            //traverse the 2 dimentional array diagonally upwards until flag is set to 1 and decrement the level
            else{
                level--;

                //once you reach the top level set flag to 0 to mark the end of the cycle.
                if(level==0)
                    flag=0;
            }
        }

        //insert the element from encrypted text when '*' is encountered in 2 dimentional array cipher, keep track of the index
        for(int i=0;i<key;i++){
            for(int j=0;j<plain.length;j++){
                if(cipher[i][j]=='*'){
                    cipher[i][j]=plain[index];
                    index++;
                }
            }
        }

        String cipher_text="";
        level=0;
        flag=0;

        //read the cipher text diagonally to retrieve the plain text back from the encrypted text.
        for(int i=0;i<plain.length;i++){
            if(cipher[level][i]!='#')
                cipher_text+=cipher[level][i];
            if(flag==0){
                level++;
                if(level==key-1)
                    flag=1;
            }
            else{
                level--;
                if(level==0)
                    flag=0;
            }
        }
        return cipher_text;
    }
}
