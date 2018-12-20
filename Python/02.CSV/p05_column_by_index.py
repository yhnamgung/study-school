# -*- coding: utf-8 -*-
"""
Created on Mon Dec 10 15:00:22 2018

@author: kosmo10
"""

#! /usr/bin/env python3
import sys
import pandas as pd
# input_file = sys.argv[1]
# input_file = sys.argv[2]
input_file = "./Data/01_data.csv"
output_file = "./Output/pout05.csv"

data_frame = pd.read_csv(input_file)
data_frame_column_by_index = data_frame.iloc[:,[0,3]]
data_frame_column_by_index.to_csv(output_file,index=False)