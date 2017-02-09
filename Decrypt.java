import java.util.Scanner;

/* Program that does a frequency analysis on a file. A key is created based on that frequency analysis. The file is then displayed using the initial key. The user of the program is then asked if they want to make a change to the key. They will be asked what character in the decrypted version of the text they want to change and what that character should decrypt to instead.
*/

public class Decrypt {


    public static void main(String[] arg) {
        // CS312 Students, do not create any other Scanners connected to System.in
        Scanner keyboard = new Scanner(System.in); 
        String fileName = getFileName(keyboard);
        String encryptedText = DecryptUtilities.convertFileToString(fileName);

        // The other method from DecryptUtilities you will have to use is
        // DecryptUtilities.getDecryptionKey(int[]), but first you need to
        // create an array with the frequency of all the ASCII characters in the 
        // encrypted text. Count ALL characters from ASCII code 0 to ASCII code 127

        // CS312 students, add you code here.
        System.out.println("The encrypted text is:");
        System.out.print(encryptedText);
        System.out.println();
        int[] counts = countCharacters(encryptedText); 
        printAscii(counts);
        char[] decryptKey = DecryptUtilities.getDecryptionKey(counts);
        printingNewChar(decryptKey);
        System.out.println("The current version of the decrypted text is: ");
        String newPassage = printNewPassage(encryptedText,decryptKey); 
        userWantsAChange(keyboard,decryptKey, encryptedText);
        keyboard.close();
    }

    // CS312 students, add your methods here

    // get the name of file to use
    public static String getFileName(Scanner kbScanner) {
        System.out.print("Enter the name of the encrypted file: ");
        String fileName = kbScanner.nextLine().trim();
        System.out.println();
        return fileName;
    }

    /*
     * This method creates an array with the frequency of all the ASCII characters in the encrypted text
     */
    public static int[] countCharacters(String text){
        int count[] = new int[128];

        for ( int i=0;i<text.length()-1;i++){
            char current = text.charAt(i); //character at position i
            count[current]++; //keeping track of frequency, whenever it encounters current, it increments the position that holds that character by 1 
        }
        return count; 
    }

    /*
     * This method prints the Ascii characters and how frequent they are 
     */ 
    public static void printAscii(int[] counts){
        System.out.println("Frequencies of characters."); 
        System.out.println("Character - Frequency");
        for (int i= 32; i<counts.length-1;i++){
            System.out.println((char)i+" - "+counts[i]); //char that is at position i, and the freuqency of that character 
        }
    }

    /*
     * This method associates the decrypted key with the characters in provided text 
     */ 
    public static void printingNewChar(char[] decrypt){
        System.out.println(); 
        System.out.println("The current version of the key for ASCII characters 32 to 126 is: "); 
        for ( int i = 32; i<127; i++){
            System.out.println("Encrypt character: "+(char)i + ","+ " decrypt character: "+decrypt[i]); //encrypt first, second is decrypted character 
        }
        System.out.println(); 
    }

    /*
     * This method prints out the new passage using the decrypted text 
     */
    public static String printNewPassage(String encryptedText, char[] decryptKey){
        System.out.println(); 
        String result = "";
        for(int i=0;i<encryptedText.length();i++){
            char current = encryptedText.charAt(i);
            result += decryptKey[current];

        }
        System.out.println(result); 
        return result; 
    }

    /*
     * This method modifies the decrypt key using the letter the user would like modified and the letter they would like that letter modified to, both those letters 
     * are swapped out 
     * This information is obtained from the userWantsAChange method 
     */
    public static void newDecryptKey(char[] decryptKey, char oldLetter, char newLetter){
        for(int i=0;i<decryptKey.length;i++){
            if(oldLetter == decryptKey[i]){ //swap old letter for new letter 
                decryptKey[i] = newLetter; 

            }
            else if(decryptKey[i] == newLetter){ //swap new letter for old letter                
                decryptKey[i] = oldLetter; 
            }
        }

    }

    
    /*
     * This method asks the user if they would like to make a change and what the specific change they would like to make is 
     * After the user decides that they no longer want ot make any changes, the final version of the passeage is printed out 
     */
    public static void userWantsAChange(Scanner keyboard,char[] decryptKey, String encryptedText) {
        System.out.println("Do you want to make a change to the key?");
        System.out.print("Enter 'Y' or 'y' to make change: ");
        String makingChanges = keyboard.nextLine(); 
        //this loop will continue so long as the user wants to keep making changes to the decrypted key 
        while (makingChanges.contains("Y") || makingChanges.contains("y")){
            System.out.print("Enter the decrypt character you want to change: "); 
            char oldLetter = keyboard.next().charAt(0); 
            System.out.print("Enter what the character "+ oldLetter +" should decrypt to instead: ");
            char newLetter = keyboard.next().charAt(0);
            System.out.println(oldLetter + "'s "+ "will now decrypt to " + newLetter+ "'s "+"and vice versa.");
            newDecryptKey(decryptKey, oldLetter, newLetter);
            System.out.println(); 
            System.out.println("The current version of the decrypted text is: ");
            printNewPassage(encryptedText, decryptKey); 
            System.out.println("Do you want to make a change to the key?");
            System.out.print("Enter 'Y' or 'y' to make change: ");
            makingChanges = keyboard.next(); 
        }

        //prints the final version of the decrypted passage 
        printingNewChar(decryptKey);
        System.out.println("The final version of the decrypted text is: ");
        printNewPassage(encryptedText,decryptKey);
        System.out.println(); 
    }
}
