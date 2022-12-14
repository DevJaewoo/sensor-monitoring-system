from time import sleep

CCS811_ADDRESS 			= 0x5B

CCS811_STATUS 			= 0x00
CCS811_MEAS_MODE 		= 0x01
CCS811_ALG_RESULT_DATA 		= 0x02
CCS811_RAW_DATA 		= 0x03
CCS811_ENV_DATA 		= 0x05
CCS811_NTC 			= 0x06
CCS811_THRESHOLDS 		= 0x10
CCS811_BASELINE 		= 0x11
CCS811_HW_ID 			= 0x20
CCS811_HW_VERSION 		= 0x21
CCS811_FW_BOOT_VERSION 		= 0x23
CCS811_FW_APP_VERSION 		= 0x24
CCS811_ERROR_ID 		= 0xE0
CCS811_APP_START 		= 0xF4
CCS811_SW_RESET 		= 0xFF

CCS811_MEAS_MODE0 		= 0x00
CCS811_MEAS_MODE1 		= 0x10
CCS811_MEAS_MODE2 		= 0x20
CCS811_MEAS_MODE3 		= 0x30
CCS811_MEAS_MODE4		= 0x40

CCS811_MEAS_DATARDY		= 0x08
CCS811_MEAS_THRESH		= 0x04

CCS811_HW_ID_CODE		= 0x81

CCS811_BOOTLOADER_APP_ERASE	= 0xF1
CCS811_BOOTLOADER_APP_DATA	= 0xF2
CCS811_BOOTLOADER_APP_VERIFY	= 0xF3
CCS811_BOOTLOADER_APP_START	= 0xF4

CCS811_STATE_READY		= 0x88

class CCS811:
	def __init__(self, i2c):
		self.i2c = i2c
		self.writeBytes(CCS811_BOOTLOADER_APP_START, [])	# APPLICATION MODE
		self.writeBytes(CCS811_MEAS_MODE, [0x10])		# 1sec Interval

		self.readBytes(CCS811_ERROR_ID, 1)			# Clear Errors

		# Until Device boot complete and data is ready
		while(self.readBytes(CCS811_STATUS, 1)[0] & CCS811_STATE_READY != CCS811_STATE_READY):
			print(self.readBytes(CCS811_STATUS, 1))
			sleep(0.5)

	def writeBytes(self, reg, data):
		self.i2c.write_i2c_block_data(CCS811_ADDRESS, reg, data)

	def readBytes(self, reg, len):
		return self.i2c.read_i2c_block_data(CCS811_ADDRESS, reg, len)

	def readData(self):
		data = self.readBytes(CCS811_ALG_RESULT_DATA, 6)

		eCO2 = (data[0] << 8 | data[1]) & (~0x8000)
		tvoc = (data[2] << 8 | data[3]) & (~0x8000)

		# I2C Read Error
		if tvoc >= 16384:
			tvoc = 0

		return [eCO2, tvoc]
