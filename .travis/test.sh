#!/usr/bin/bash bash

data="tetet [UPLOAD PACKAGE]"

if [[ $data == *"[UPLOAD PACKAGE]"* ]] ; then
	echo 'Found match! line starts with 'chr'\n'
else
  	echo 'Not found'

fi