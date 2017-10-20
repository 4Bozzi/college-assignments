#include <stdio.h>
#include <string.h>
#include <math.h>

// includes cuda libraries
#include <cutil.h>

// includes kernel
#include <template_kernel.cu>
//#include <CNU_kernel.cu>
//#include <VNU_kernel.cu>



void ldpc( int argc, char** argv);                                  //Function called from main


//All of the following functions are located in .cpp file
extern "C" 
void computeNumSeq(int*, char*);
extern "C" 
void create_Host_Array(char*, short int*, int);
extern "C" 
void write_Results(char*, char*, int*);
extern "C" 
void create_Offset_Array(short int*, int);


/*______________________________________________Program Main___________________________________________________*/


int main( int argc, char** argv)
{
    ldpc( argc, argv);

    CUT_EXIT(argc, argv);
}




/*______________________________________________ldpc function_________________________________________________*/

void
ldpc( int argc, char** argv)
{

//Searches for first available CUDA device
    CUT_DEVICE_INIT(argc, argv);



/*________________________________Allocate memory on host to hold file name______________________________________*/


//Allocate memory for the files location/path on PC limited to 200 characters
char* database_Path = (char*) malloc(sizeof(char) * 200);





/*________________________________Allocate memory on host for integer Array______________________________________*/

//integer array on host. use short ints to save memory
short int* host_array = (short int*) malloc(sizeof(short int) * 100);




/*________________________________Allocate memory on Device for integer Array____________________________________*/

//integer array on device. use short ints to save memory
short int* device_array;
short int* device_offset;
CUDA_SAFE_CALL(cudaMalloc((void**) &device_array, (100 * sizeof(short int)) ));





/*______________________________________Create Array on host from File___________________________________________*/




/*_______________________________________Copy Host Array Contents to Device Array_________________________________*/





/*________________________________Allocate memory on host for offset Array______________________________________*/




/*________________________________Allocate memory on Device for offset Array____________________________________*/



/*______________________________________Create Array on host___________________________________________*/






/*_______________________________________Copy Host Array Contents to Device Array_________________________________*/






/*___________________________Start timer to measure kernel execution time______________________________________*/


//Start Timer
unsigned int timer = 0;                                //Create a variable timer and set it to zero
CUT_SAFE_CALL( cutCreateTimer( &timer));               //Creates a timer and sends result to variable timer
CUT_SAFE_CALL( cutStartTimer( timer));                 //Starts the execution of the timer

printf( "\nLaunching Kernel... \n");




/*_________________________________Setup Execution Parameters For CNU Kernel____________________________________*/


//Number of Threads per block
int numThreads = 256;
int numBlocks = 48;

// setup execution parameters
dim3 threads(numThreads);
dim3 grid(numBlocks);


/*_____________________________________________Execute CNU Kernel__________________________________________________*/


// execute the kernel
//CNU_kernel<<< grid, threads>>>(device_array, device_offset);
template_kernel<<< grid, threads>>>(device_array, device_offset);



/*_________________________________Setup Execution Parameters For VNU Kernel____________________________________*/





/*_____________________________________________Execute VNU Kernel__________________________________________________*/





/*_______________________________________Copy Results from GPU to Host_________________________________________*/




/*_______________________________________________Check Results_________________________________________________*/







/*______________________________________________Clean Up Data__________________________________________________*/


    //Free Device Memory
    CUDA_SAFE_CALL(cudaFree(device_array));
}