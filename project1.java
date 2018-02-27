import java.io.*;
import java.util.*;

public class project1 
{

     public static void main(String[] args) 
     {
        
        //data
        int k = 0;
        String currentline;
        int n = 0;
        //get file name from command line argument
     	File input = null;
        if (0 < args.length) 
        {
            input = new File(args[0]);
        }

        //try catch statement to read number of lines from input file
        try 
        {
            //initialize buffered reader
            BufferedReader br = new BufferedReader(new FileReader(input));

            //k is the first number in the input file, use for kway merge sort
            k = Integer.parseInt(br.readLine());
            System.out.println ("K: " + k);

            //use n to find the size of the input/number of lines in input file
            //System.out.println("Unsorted Numbers: ");
            while ((currentline = br.readLine()) != null) 
            {
                //System.out.print(currentline.trim()+ " ");
                n++;
            }
            System.out.println ("N: "+ n);
            //reset and close buffered reader so i can open it again and use it to read into an array of size n
            br.reset();
            br.close();
        }
        //catch statement if file is not found
        catch (IOException f) 
        {
            System.out.println();
        } 

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(input));
            String currline;
            int i;
            int index =0;
            //now I have found n and k so I will create an array with n elements and populate it with the input data
            int [] data = new int [n];

                k = Integer.parseInt(br.readLine());
                while ((currline = br.readLine()) != null && index<n)
                {
                    i = Integer.parseInt(currline.trim());
                    data[index] = i;
                    //System.out.print(data[index] + " ");
                    index++;
                }
                System.out.println();

            //instance of class
            project1 merge = new project1();
            int [] mergedata = merge.merge (data, 0, data.length - 1, k);

            //print sorted array to file
            String filename = "numbers_sorted";
            FileWriter fw = new FileWriter(filename);
            PrintWriter pw = new PrintWriter(fw);
            for (int z = 0; z<mergedata.length-1; z++)
            {
                pw.print(mergedata[z] + " ");
            }
        }

        //catch statement if file not found
        catch (IOException f) 
        {
            System.out.println("File not found.");
        } 

    }
    //low and high are the indexes 0 and n-1 because n is length of data
    //merge recursive function to call merge function
    public static int [] merge (int[] data, int low, int high, int k) 
        {
            if (low < high) 
            {
                for (int i = 0; i < k; i++) 
                {
                    //call merge with appropriate parameters
                    merge (data, low + ((high-low+1)*i)/k, low + (high-low+1)*(i+1)/k - 1, k);
                }
                mergesort (data, low, high, k);
            }
            return data;
        }

    //mergesort function to carry out kway merge sort
    //will sort the subarrays, replace them back in original array, then merge all n/k subarrays in original array
    public static void mergesort(int [] data, int low, int high, int k)
    {
        int n = data.length;

        //if k is odd, will split into k-1 subarrays
        if (k/n != 0)
        {
            k = k-1;
        }

        //subarray has n/k elements, there will be k subarrays
        int [] subarray = new int [(n/k)+1];

        int o = 0;

        //create and sort the n/k subarrays

        //populate subarray then sort it
        for (int j = 0; j<k; j++)
        {
            //populate subarray
            for (int p = 0; p<n/k-1; p++)
            {
                subarray[j] = data[p];
            }
            //sort subarray
            int [] sortedsub = new int [(n/k)+1];
            int midindex = (n/k/2)+1;
            int lowindex = 0;
            int highindex = n/k;
            int y = 0;

            //not exactly k way-- this is similar to binary
            while (lowindex<=highindex)
            {
                if (lowindex >= midindex)
                {
                    sortedsub [y] = subarray [highindex];
                    y=y+1;
                    highindex = highindex+1;
                }
                else if (highindex >= subarray[highindex])
                {
                    sortedsub [y] = subarray [lowindex];
                    y=y+1;
                    lowindex = lowindex+1;
                }   
                else if (subarray[low]<= subarray[high])
                {
                    sortedsub[y] = subarray[high];
                    y = y+1;
                    lowindex = lowindex+1;
                }
                else
                {
                    sortedsub[y] = subarray[highindex];
                    y = y+1;
                    highindex = highindex+1;
                }
                for (int z = 0; z<=highindex-1; z++)
                {
                    subarray[z]=sortedsub[z];               
                }
            }
            high = highindex;
            low = lowindex;
            //replace sorted subarray in original array, data
            //run this for look n/k times but start at index o
            for (int t = 0; t < subarray.length-1; t++)
            {
                data [o] = subarray[t];
                o++;
            }
            //add n/k to o to start adding from that index next time, as to fill the whole array
        }
        o += (n/k);
        //now data will be k*n/k sorted subarrays one after the other, so this will be all n elements
        //merge the k subarrays together
        //start an index 0, 0+(n/k), 0+2(n/k), and so on until k (find formula to calculate these)
        int ind = 0;
        int x=0;
        int y=0;
        while (ind < data.length-1)
        {
            //runs k times--attempt at merging k sorted arrays
            for (int i = 0; i < k; i++)
            {
                //check the first index and so on in each subarray using this formula
                if (data[ind] < data[ind + i*(n/k)])
                { 

                    //not swapping values, move it into the space next to ind and move everything down one
                    for(int m = data.length-ind; m >= 0 ; m--)
                    {
                        if (m == ind)
                        {
                            data [ind + i*(n/k)] = y; 
                            data[ind+1] = y;
                        }
                        else
                        {
                            data[m+1] = data [m];
                        }
                    }
                }
                else if (data[ind] == data[ind + i*(n/k)]) 
                {   
                    //move it into the space next to data[ind], so data [ind+1], but move everything else in data over one index
                    for( int j = data.length-ind; j>= 0 ; j-- )
                    {
                        if (j == ind)
                        {
                            data [ind + i*(n/k)] = y; 
                            data[ind+1] = y;
                        }
                        else
                        {
                            data[j+1] = data [j];
                        }
                    }
                }
                else
                {
                    //this means data[ind] < data[ind + i*(n/k)]
                    //move data[ind + i*(n/k)] into data [ind] and move everything else to the right
                    for( int z = data.length-ind; z>= 0 ; z-- )
                    {
                        if (z == ind)
                        {
                            data [ind + i*(n/k)] = y; 
                            data[ind] = y;
                        }
                        else
                        {
                            data[z+1] = data [z];
                        }
                    }
                }
            }
        }
    }
}




