##!/usr/bin/bash bash

if [ "$TRAVIS_TEST_RESULT" -eq 0 ]; then
   echo "$TRAVIS_COMMIT_MESSAGE"
   ./gradlew -PbintrayUser="$BINTRAY_USER" -PbintrayApiKey="$BINTRAY_API_KEY" bintrayUpload

   else
     echo "Test failed"
fi
