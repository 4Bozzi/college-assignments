
/*_____________________________________________________________________________________________________________*/
/*                                                                                                             */
/*                                         ldpc.cpp                                                            */
/*_____________________________________________________________________________________________________________*/




/*____________________________________________Export C Interface_______________________________________________*/


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
int calculate_Sign(short int* signArray, int* number_of_integers, short int* signCalcArray );


#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <iostream>
#include <fstream>
#include <math.h>
using std::cerr;
using std::cout;
using std::endl;
using std::ifstream;


/*____________________________________________set number_of_integers___________________________________________*/
void
compute_Number_Of_Integers(int* number_of_integers)
{
	int fileNum = 0;					     //Contains ascii value of current character value
	int i = 0;

	ifstream indata;  
	indata.open("data.txt"); // opens the file
	if(!indata) { // file couldn't be opened
		cerr << "Error: file could not be opened" << endl;
		exit(1);
	}
	indata >> fileNum;
	i++;
	while ( !indata.eof() ) { // keep reading until end-of-file
		indata >> fileNum; // sets EOF flag if no value found
		i++;
	}
	*number_of_integers = i;
	indata.close();
	cout << "End-of-file reached.." << endl;
}


/*_____________________________________________create_Host_Array_______________________________________________*/


void
create_Host_Array(short int* databaseArray)
{
	short int fileNum = 0;					     //Contains ascii value of current character value
	int i = 0;

	ifstream indata;  
	indata.open("data.txt"); // opens the file
		if(!indata) { // file couldn't be opened
			cerr << "Error: file could not be opened" << endl;
			exit(1);
		}
	indata >> fileNum;
	databaseArray[i] = fileNum;
	i++;
	while ( !indata.eof() ) { // keep reading until end-of-file
		indata >> fileNum; // sets EOF flag if no value found
		databaseArray[i] = fileNum;
		i++;
	}
	indata.close();
	cout << "End-of-file reached.." << endl;
}


/*________________________________________________Offset Array___________________________________________________*/

void
create_Offset_Array(short int* offsetArray, int* number_of_integers)
{
	for(int i = 0; i < *number_of_integers; i++){
		offsetArray[i] = 0;
	}
}




/*________________________________________________Results Array___________________________________________________*/

void
create_Results_Array(short int* resultsArray, int* number_of_integers)
{
	for(int i = 0; i < (*number_of_integers)*2; i++){
		resultsArray[i] = 0;
	}
}


/*________________________________________________Sign Array___________________________________________________*/

void
create_VNSign_Array(short int* signArray, int* number_of_integers)
{
	for(int i = 0; i < *number_of_integers; i++){
		signArray[i] = 0;
	}
}



/*__________________________________________Get file for results_______________________________________________*/

void
write_Results(short int* databaseArray, char* Results_Path, int* number_of_integers)
{
	// Open the database file for reading
	printf("\nPlease enter the path to the Location for Results Storage:\n");
	scanf("%s",Results_Path);



	FILE* resultsFile;                            //Create a pointer to the file
	resultsFile = fopen(Results_Path, "w");       //Open the file for reading

	if(resultsFile == NULL)                       //Print error message if file cannot be opened
	do{
	printf("\nError: File Could Not be Opened. \n");
	printf("\nPlease enter the path to the Location for Results Storage:\n");
	scanf("%s",Results_Path);
	resultsFile = fopen(Results_Path, "w");       //Open the file for reading
	}while(resultsFile == NULL);

	printf("\nFile Opened Successfully for writing:\n");


	for(int i = 0; i < number_of_integers[0]; i++){
		fprintf(resultsFile, "%d\n", databaseArray[i]);
	}
}


int
calculate_Sign(short int* signArray, int* number_of_integers, short int* signCalcArray ){
	int size = (sizeof(signCalcArray)/sizeof(short int));
	
	for(int i = 0; i < *number_of_integers; i+=3){
		signCalcArray[i/3] = (signArray[i]^signArray[i+1]^signArray[i+2]);
	}

	int prevOr = signCalcArray[0];

	for(int j = 1; j < size; j++){
		prevOr = prevOr|signCalcArray[j];
	}
	return prevOr;
}

void
sequential_CNVN(short int* database_array, int* number_of_integers, short int* sign_array, short int* results_array)
{
	short int input1 = 0;
	short int input2 = 0;
	short int input3 = 0;
	short int min1 = 0;
	short int min2 = 0;
	short int agr = 1;
	short int sign = 0; 

	for(int i = 0; i < number_of_integers[0]; i += 2){
		
		input1 = results_array[i];
		input2 = results_array[i + (1)];
		input3 = database_array[i/2];
		
		short int sum = (input1 + input2 + input3);
		
		min1 = (sum - input1);
		min2 = (sum - input2);

		if(sum < 0){
			sign = 1;
		}

		results_array[i] = min1;
		results_array[i + (1)] = min2;
		sign_array[i] = sign;
		sign_array[i + (1)] = sign;
	}
	for(int i = 0; i < number_of_integers[0]; i += 3){
		short int input1 = results_array[i];
		short int input2 = results_array[i + (1)];
		short int input3 = results_array[i + (2)];
		min1 = 0;
		min2 = 0;

		agr = 1;

		if(input1 < 0){
			agr = agr*(-1);
		}
		if(input2 < 0){
			agr = agr*(-1);
		}
		if(input3 < 0){
			agr = agr*(-1);
		}
		
		//Check first two inputs to get initial min1 and min2
		if(abs(input1) <= abs(input2)){
			min1 = input1;
			min2 = input2;
		}
		else{
			min1 = input2;
			min2 = input1;
		}

		//Check input3 against min1 and min2
		if(abs(input3) <= abs(min1)){
			min2 = min1;
			min1 = input3;
		}
		else
		if(abs(input3) <= abs(min2)){
			min2 = input3;
		}

		results_array[i] = min2*agr;
		results_array[i +(1)] = min1*agr;
		results_array[i +(2)] = min1*agr;
	}
}


