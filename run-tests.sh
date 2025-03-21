#!/bin/sh

function header(){
	output=$1
	for i in {1..80} 
	do
		echo -n "-" >> $output
	done
	echo >> $output
}

echo
echo "Compiling the source code"
echo
javac *.java

if ! test -f HashtableExperiment.class
then
	echo
	echo "HashtableExperiment.class not found! Not able to test!! "
	echo
	exit 1
fi

# Define the load factors to test
load_factors=("0.5" "0.6" "0.7" "0.8" "0.9" "0.95" "0.99")
debugLevel=1

# Test for word-list (dataSource = 3) with varying load factors
echo
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Running tests for word-list (dataSource = 3) for varying load factors"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo

for load in "${load_factors[@]}"
do
    echo "Running java HashtableExperiment dataSource = 3 loadFactor = $load "
    java HashtableExperiment 3 $load $debugLevel >> /dev/null
    dos2unix linear-dump.txt double-dump.txt >& /dev/null

    echo diff -w -B linear-dump.txt test-cases/word-list-$load-linear-dump.txt ">" diff-linear-$load.out
    diff -w -B linear-dump.txt test-cases/word-list-$load-linear-dump.txt > diff-linear-$load.out
    if test "$?" = 0
    then
        echo "Test PASSED for linear probing and load = $load"
        /bin/rm -f diff-linear-$load.out
    else
        echo "==> Test FAILED for linear probing load = $load!! "
        echo "       Check the file diff-linear-$load.out for differences"
    fi

    echo diff -w -B double-dump.txt test-cases/word-list-$load-double-dump.txt ">" diff-double-$load.out
    diff -w -B double-dump.txt test-cases/word-list-$load-double-dump.txt > diff-double-$load.out
    if test "$?" = 0
    then
        echo "Test PASSED for double probing and load = $load"
        /bin/rm -f diff-double-$load.out
    else
        echo "==> Test FAILED for double probing load = $load!! "
        echo "       Check the file diff-double-$load.out for differences"
    fi
    echo
done
