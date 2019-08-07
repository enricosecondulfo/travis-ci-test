##!/usr/bin/bash bash

if [ $TRAVIS_TEST_RESULT -eq 0 ]; then
   echo "Test succeded"

   else
     echo "Test failed"
fi
