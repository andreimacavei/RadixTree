#!/bin/bash

if [ $# -eq 1 ]
then
	java -cp bin $1 in/Test1_index in/Test1_prefix > out/Test1_out
	java Comparator ref/Test1_ref out/Test1_out


	java -cp bin $1 in/Test2_index in/Test2_prefix > out/Test2_out
	java Comparator ref/Test2_ref out/Test2_out

	java -cp bin $1 in/Test3_index in/Test3_prefix > out/Test3_out
	java Comparator ref/Test3_ref out/Test3_out

	java -cp bin $1 in/Test4_index in/Test4_prefix > out/Test4_out
	java Comparator ref/Test4_ref out/Test4_out

	java -cp bin $1 in/Test5_index in/Test5_prefix > out/Test5_out
	java Comparator ref/Test5_ref out/Test5_out
	

	java -cp bin $1 in/Test6_index in/Test6_prefix > out/Test6_out
	java Comparator ref/Test6_ref out/Test6_out

	java -cp bin $1 in/Test7_index in/Test7_prefix > out/Test7_out
	java Comparator ref/Test7_ref out/Test7_out

	java -cp bin $1 in/Test8_index in/Test8_prefix > out/Test8_out
	java Comparator ref/Test8_ref out/Test8_out

	java -cp bin $1 in/Test9_index in/Test9_prefix > out/Test9_out
	java Comparator ref/Test9_ref out/Test9_out

	java -cp bin $1 in/Test10_index in/Test10_prefix > out/Test10_out
	java Comparator ref/Test10_ref out/Test10_out
	
else
	echo "Usage: ./checker.sh <name_of_the_class_with_main>"
fi
