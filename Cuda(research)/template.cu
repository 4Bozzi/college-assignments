#include <stdio.h>
#include <string.h>
#include <math.h>

// includes cuda libraries
#include <cutil.h>
#include <iostream>


// includes kernel
#include <template_kernel.cu>
//#include <CNU_kernel.cu>
//#include <VNU_kernel.cu>



void ldpc( int argc, char** argv);                                  //Function called from main


//All of the following functions are located in .cpp file
extern "C" 
void compute_Number_Of_Integers(int*);
extern "C" 
void create_Host_Array(short int*);
extern "C" 
void write_Results(short int*, char*, int*);
extern "C" 
void create_Offset_Array(short int* offsetArray, int* number_of_integers);
extern "C" 
void sequential_CNVN(short int*, int*, short int*, short int*);
extern "C" 
void create_Results_Array(short int* results_array, int* number_of_integers);
extern "C" 
void create_VNSign_Array(short int* signArray, int* number_of_integers);
extern "C" 
int calculate_Sign(short int* sign_array, int* number_of_integers, short int* signcalc_array );


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

int sign = 0;


/*______________________________________________compute number_of_integers_________________________________________________*/
int number_of_integers = 0;
compute_Number_Of_Integers(&number_of_integers);
std::cout << "Number of integers: " << number_of_integers << std::endl;




/*________________________________Allocate memory on host to hold file name______________________________________*/
//Allocate memory for the files location/path on PC limited to 200 characters
char* database_Path = (char*) malloc(sizeof(char) * 200);




/*________________________________Allocate memory on host for RESULTS Array______________________________________*/
//integer array on host. use short ints to save memory
short int* host_results_array = (short int*) malloc(sizeof(short int) * number_of_integers * 2);




/*________________________________Create RESULTS Array______________________________________*/
//sets all of the values to 0 initialy
create_Results_Array(host_results_array, &number_of_integers);

////////////////////////////////////////////////////////////////////////////////////////////////
//Testing to make sure that results array now contains all zeros
//	for(int cnt = 0; cnt < number_of_integers; cnt++){
//		printf("resultsArray[%d] = %d\n", cnt, results_array[cnt]);
//	}
////////////////////////////////////////////////////////////////////////////////////////////////


/*________________________________Allocate memory on device for RESULTS Array______________________________________*/
short int* results_array = (short int*) malloc(sizeof(short int) * number_of_integers * 2);
CUDA_SAFE_CALL(cudaMalloc((void**) &results_array, ((number_of_integers * sizeof(short int))*2) ));




/*________________________________Allocate memory on host for SIGN Array______________________________________*/
//integer array on host use short ints to save memory
short int* sign_array = (short int*) malloc(sizeof(short int) * number_of_integers);




/*__________________________________________Create SIGN Array__________________________________________________*/
//sets all of the values to 0 initialy
create_VNSign_Array(sign_array, &number_of_integers);

////////////////////////////////////////////////////////////////////////////////////////////////
//Testing to make sure that sign array now contains all zeros
//	for(int cnt = 0; cnt < number_of_integers; cnt++){
//		printf("signArray[%d] = %d\n", cnt, sign_array[cnt]);
//	}
////////////////////////////////////////////////////////////////////////////////////////////////



/*________________________________Allocate memory on device for SIGN Array______________________________________*/
short int* device_sign_array = (short int*) malloc(sizeof(short int) * number_of_integers);
CUDA_SAFE_CALL(cudaMalloc((void**) &device_sign_array, (number_of_integers * sizeof(short int)) ));





/*__________________________Allocate memory on host for SIGN CALULATIONS Array__________________________________*/
short int* signcalc_array = (short int*) malloc(sizeof(short int) * (number_of_integers/3));




/*________________________________Allocate memory on host for OFFSET Array______________________________________*/

short int* host_offset_array = (short int*) malloc(sizeof(short int) * number_of_integers);




/*__________________________________________Create OFFSET Array__________________________________________________*/
//sets all of the values to 0 initialy
create_Offset_Array(host_offset_array, &number_of_integers);

////////////////////////////////////////////////////////////////////////////////////////////////
//Testing to make sure that offset array now contains all zeros
//	for(int cnt = 0; cnt < number_of_integers; cnt++){
//		printf("offsetArray[%d] = %d\n", cnt, offset_array[cnt]);
//	}
////////////////////////////////////////////////////////////////////////////////////////////////



/*________________________________Allocate memory on Device for OFFSET Array______________________________________*/

//offset array on device. use short ints to save memory
short int* offset_array = (short int*) malloc(sizeof(short int) * number_of_integers);
CUDA_SAFE_CALL(cudaMalloc((void**) &offset_array, (number_of_integers * sizeof(short int)) ));




/*________________________________Allocate memory on host for INTEGER Array______________________________________*/

//integer array on host. use short ints to save memory
short int* host_array = (short int*) malloc(sizeof(short int) * number_of_integers);



/*________________________________Allocate memory on Device for INTEGER  Array____________________________________*/

//integer array on device. use short ints to save memory
short int* device_array = (short int*) malloc(sizeof(short int) * number_of_integers);
CUDA_SAFE_CALL(cudaMalloc((void**) &device_array, (number_of_integers * sizeof(short int)) ));





/*____________________________________Create INTEGER Array on host from File________________________________________*/
//Read integers into array located on the PC using a function which you will create in the .cpp file
create_Host_Array(host_array);

////////////////////////////////////////////////////////////////////////////////////////////////
//Testing to make sure that integer array now contains all zeros
//	for(int cnt = 0; cnt < number_of_integers; cnt++){
//		printf("hostArray[%d] = %d\n", cnt, host_array[cnt]);
//	}
////////////////////////////////////////////////////////////////////////////////////////////////



/*_______________________________________Copy Host Array Contents to Device Array_________________________________*/
// copy host memory database array to allocated device array
CUDA_SAFE_CALL(cudaMemcpy(device_array, host_array, (number_of_integers * sizeof(short int)), cudaMemcpyHostToDevice));




/*_________________________________Setup Execution Parameters For VNU Kernel____________________________________*/
//Number of Threads per block
short int numThreads = 64;
short int numBlocks = 96;

// setup execution parameters
dim3 threads(numThreads);
dim3 grid(numBlocks);

/*___________________________Start timer to measure kernel execution time______________________________________*/
printf( "\nLaunching Multithreaded Kernel... \n");
//Start Timer
unsigned int timer = 0;                                //Create a variable timer and set it to zero
CUT_SAFE_CALL( cutCreateTimer( &timer));               //Creates a timer and sends result to variable timer
CUT_SAFE_CALL( cutStartTimer( timer));                 //Starts the execution of the timer




/*__________________________________________Execute VNU Kernel_________________________________________________*/
// execute the vn kernel
VNU_kernel<<<grid, threads>>>(device_array, offset_array, device_sign_array, results_array);




/*_________________________________Setup Execution Parameters For CNU Kernel____________________________________*/
//Number of Threads per block
//short int numThreads = 32;
//short int numBlocks = 3;

// setup execution parameters
//dim3 threads(numThreads);
//dim3 grid(numBlocks);


/*_________________________________________Execute CNU Kernel___________________________________________________*/
CNU_kernel<<<grid, threads>>>(offset_array, results_array);




/*_____________________________________________Check Results____________________________________________________*/
//Stop Timer
CUT_SAFE_CALL( cutStopTimer( timer));
printf( "\nGPU database scan time: %f (ms)\n", cutGetTimerValue( timer));
CUT_SAFE_CALL( cutDeleteTimer( timer));




/*_______________________________________Copy Results from GPU to Host__________________________________________*/
printf( "\nCopying Results from GPU to host... \n");

//Copy Results from GPU
CUDA_SAFE_CALL(cudaMemcpy(host_array, results_array, (number_of_integers * sizeof(short int)), cudaMemcpyDeviceToHost));
CUDA_SAFE_CALL(cudaMemcpy(sign_array, device_sign_array, (number_of_integers * sizeof(short int)), cudaMemcpyDeviceToHost));



/*_____________________________________Sign Array Calculations___________________________________________________*/
sign = calculate_Sign(sign_array, &number_of_integers, signcalc_array );


printf( "\nThe value of the sign multithreaded operations is: %d\n", sign);



/*_______________________________Copy Results from Host to Global Memory_______________________________________*/
char path[20];
write_Results(host_array, path, &number_of_integers);








/*---------------------------------Sequential Version for Comparison-------------------------------------------*/

//resets host arrays to original values
create_Host_Array(host_array);
create_Offset_Array(host_offset_array, &number_of_integers);
create_VNSign_Array(sign_array, &number_of_integers);
create_Results_Array(host_results_array, &number_of_integers);


/*___________________________Start timer to measure kernel execution time______________________________________*/

printf( "\nLaunching Sequential Kernel... \n");
//Start Timer
timer = 0;										       //Create a variable timer and set it to zero
CUT_SAFE_CALL( cutCreateTimer( &timer));               //Creates a timer and sends result to variable timer
CUT_SAFE_CALL( cutStartTimer( timer));                 //Starts the execution of the timer


//make a call to the sequential version
sequential_CNVN(host_array, &number_of_integers, sign_array, host_results_array);



/*_______________________________________________Check Results_________________________________________________*/

//Stop Timer
CUT_SAFE_CALL( cutStopTimer( timer));
printf( "\nCPU database scan time: %f (ms)\n", cutGetTimerValue( timer));
CUT_SAFE_CALL( cutDeleteTimer( timer));

sign = calculate_Sign(sign_array, &number_of_integers, signcalc_array);
printf( "\nThe value of the sign sequential operations is: %d\n", sign);

/*_______________________________Copy Results from Host to Global Memory_______________________________________*/
write_Results(host_results_array, path, &number_of_integers);


/*----------------------------------------End Sequencial Version-----------------------------------------------*/









/*______________________________________________Clean Up Data__________________________________________________*/

    //Free Device Memory
    CUDA_SAFE_CALL(cudaFree(device_array));
}