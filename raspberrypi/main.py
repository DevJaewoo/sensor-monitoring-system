import smbus
from time import sleep
from CCS811 import CCS811
from MPU6050 import MPU6050

i2c = smbus.SMBus(1)

ccs811 = CCS811(i2c)
sensor = MPU6050(i2c)

sleep(0.1)

while True:
	try:
		[eco2, tvoc] = ccs811.readData()
		print('eCO2: {} TVOC: {}'.format(eco2, tvoc))
		print(sensor.get_accel_data())
	except:
		print('Except')

	sleep(1)
