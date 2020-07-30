
import java.util.Scanner;
import java.util.Vector;

class Matris {
    private int numberOfVertices ;
    Vector<Integer> Edges = new Vector<Integer>();
    Vector<Integer> src = new Vector<Integer>();
    Vector<Integer> dest = new Vector<Integer>();
    
    //to import Sorce, Destination and Weigh vectors into one array
    private int [][] SDW ;

    // this array represents parent and children relations
    // rows represent parent vertices ( except for [0])
    // coloumns represent children vertices ( except for [0])
    private int [][] parChild ;

    // size array is meant to hold the size of each tree that each parent is holding  
    private int [] size ;


    // to save SDW elements that do not create cycle for the final MST
    private int [][] Final;
   
    // initialized this way to work properly in for()s
    int Vi = 0 , Vf = 0  , weight = -2 ;
    Scanner scanner = new Scanner ( System.in ) ;
    
    public void setNOV () {

        System.out.println("tedade ras haye graph ra vared konid:");
        System.out.println(" deghat konid ke bishtar az 2 ras bashand");

        this.numberOfVertices = scanner.nextInt();
        
        for (; numberOfVertices <3;){
            System.out.println("lotfan deghat konid tedade ras ha bishtar az 2 bashad");
            this.numberOfVertices = scanner.nextInt(); 
        }
    }

    // to receive src, dest and weight of edges
    public void matrisGo () {
        boolean go = true ;
        for (;go;){
            if ( go ){
                for (Vi = 0 ; Vi < 1 || Vi > numberOfVertices ;)
                {
                    System.out.print( "Src:\t");
                    Vi = scanner.nextInt();
                    if (Vi == 99) {
                        go = false;
                        break;
                    }
                }
            }
            if (go){
                for (Vf = 0 ; Vf < 1 || Vi > numberOfVertices ;)
                {
                    System.out.print( "Dest:\t");
                    Vf = scanner.nextInt();
                }
            }
            if(go){
                for (weight = -1 ; weight < 0 ;)
                {
                    System.out.print("Weight:\t");
                    weight = scanner.nextInt();
                    Edges.add(weight);
                    src.add(Vi) ;
                    dest.add(Vf) ;
                }
            }
            
        }

        // rows go up to number of edges
        // coloumns are Src, Dest, Weight
        SDW = new int [Edges.size()][3];

        for ( int i = 0 ; i < Edges.size() ; i++){
            SDW [i][0] = src.get(i);
            SDW [i][1] = dest.get(i);
            SDW [i][2] = Edges.get(i);
        }

        // to free space
        Edges.removeAllElements();
        src.removeAllElements();
        dest.removeAllElements();
        sortSDW();
    }
    
    // simply, to explain how the program works
    public boolean explain () {
        boolean stuation =false ;
        String command ;
        for (;!stuation;){
        System.out.println("lotfan be tartibe 'Src' Enter 'Dest' Enter 'Weigh' vared konid: ");
        System.out.println("deghat konid shomare ras ra dorost vared konid");
        System.out.println(" 1 <= shomare ras <= tedade ras ha");
        System.out.println("vazn (weight) nemitavanad manfi bashad");
        System.out.println("baraye etmame meghdar dehi be jaye Src 99 ra vared konid");
        System.out.println("dar soorate khandane raveshe kar 'ok' ra vared konid");
        command = scanner.next();
        if ( command.compareTo("ok") == 0)
        stuation = true ;
    }
        return stuation;
    }

    //to sort the SDW array and sync coloumns together
    public void sortSDW () {
        int [] temp;
        temp = new int [3] ;
        for ( int i = 0 ; i < SDW.length ; i ++) {
            for ( int j = i + 1  ; j < SDW.length ; j++ ){
                if (SDW[i][2] > SDW[j][2]) 
                {
                    // to sort weight
                    temp [2] = SDW[i][2];
                    SDW[i][2] = SDW[j][2];
                    SDW[j][2] = temp [2];

                    // to sync Sources with the Weight
                    temp [0] = SDW[i][0];
                    SDW[i][0] = SDW[j][0];
                    SDW[j][0] = temp [0];

                    // to sync Destinations with Weight
                    temp [1] = SDW[i][1];
                    SDW[i][1] = SDW[j][1];
                    SDW[j][1] = temp [1];
                }
            }
        }

    }

    // returns the parent index of the given child index
    public int par ( int childIndex ) {
        
        int c = childIndex ;

        for ( int i = 1 ; i < numberOfVertices + 1 ; i ++ ) {
            if ( parChild[i][c] == 1 ){
                return i ;
            }
        }
        return 0 ;

    }
    
    // puts the smaller array ( parChild[c][] )
    // into the larger array ( parChild[p][] )
    public void merge ( int p , int c ) {

        for ( int i = 1 ; i < numberOfVertices + 1 ; i ++ ) {
            parChild[p][i] += parChild[c][i] ; 
            parChild [c][i] = 0 ;
        }
        size [p] += size [c] ;
        size [c] = 0 ;

    }

    public void krus () {

       parChild = new int [numberOfVertices+1][numberOfVertices+1];
       size = new int [numberOfVertices+1] ;
       Final = new int [numberOfVertices-1][3] ;

       for ( int i = 1 ; i < size.length ; i ++ ){
           parChild[i][i] = 1 ;
           size[i] = 1 ;
       }

       // parent of src, parent of dest
       int src , parS ; 
       int dest , parD ; 

       for ( int i = 0 , j = 0 ; i < SDW.length && j < (numberOfVertices - 1) ; i++ ) 
       {
           src = SDW[i][0] ; 
           dest = SDW[i][1] ;
           parS = par(src) ;
           parD = par(dest) ;
           if ( parS == parD ) {
               //here
               System.out.println("yal e " + src + " ==> " + dest + " ( Weight = " + SDW[i][2] + " )"+" dor ijad mikonad");
           }
           else {
               if ( size [parS] >= size [parD] ) {
                   merge(parS, parD);
               }
               if ( size [parD] > size [parS] ) {
                   merge(parD, parS);
               }
               Final [j][0] = src ; 
               Final [j][1] = dest ;
               Final [j][2] = SDW [i][2] ;
               j ++ ;
           }
       }
        
    }

    public void show () {
        System.out.println("\nminimum spanning tree looks something like this:\n");
        System.out.println("==================================");
        System.out.println("index\tSrc\tDest\tWeight");
        for ( int i = 0 ; i < Final.length ; i ++ ){
            System.out.println( (i+1) + "\t" +Final[i][0] + "\t" + Final[i][1] + "\t" + Final[i][2]);
        }
        System.out.println("==================================");
    }
    
}

public class kruskal {

    public static void main(String[] args) {
        Matris matrix = new Matris ();
        matrix.setNOV();
        boolean situation ;
        Scanner scnr = new Scanner (System.in);
        situation = matrix.explain();
        if (situation)
        {
            matrix.matrisGo() ;
        }
        scnr.close();
        matrix.krus();
        matrix.show();
        
    }
}