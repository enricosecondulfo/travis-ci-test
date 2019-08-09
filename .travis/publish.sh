##!/usr/bin/bash bash

if [ $TRAVIS_TEST_RESULT -eq 0 ]; then
   ./gradlew -PbintrayUser=$BINTRAY_USER -PbintrayApiKey=$BINTRAY_API_KEY bintrayUpload

   else
     echo "Test failed"
fi
