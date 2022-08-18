from time import sleep
from mpu6050 import mpu6050

import numpy as np

class MPU6050:
	def __init__(self, i2c):
		self.i2c = i2c
		self.mpu6050 = mpu6050(0x68)
		sleep(0.1)
		i2c.write_i2c_block_data(0x68, 0x6B, [0x20])
		sleep(0.1)


	def get_accel_data(self):
		return self.mpu6050.get_accel_data()

	def get_temp_data(self):
		data = self.i2c.read_i2c_block_data(0x68, 0x41, 2)
		temp = np.int16(data[0] << 8 | data[1]) / 340.0 + 36.53;
		return temp
