import requests

import smbus
from time import sleep
from CCS811 import CCS811
from MPU6050 import MPU6050

import traceback

SERVER_IP = '192.168.166.112'
SERVER_PORT = 8080
CLIENT_ID = 1
REQUEST_URL = 'http://{0}:{1}/api/{2}/sensor'.format(SERVER_IP, SERVER_PORT, CLIENT_ID)

SENSOR_INTERVAL = 1 # sec

i2c = smbus.SMBus(1)

ccs811 = CCS811(i2c)
sensor = MPU6050(i2c)

sleep(0.1)

while True:

	sleep(SENSOR_INTERVAL)

	try:
		[eco2, tvoc] = ccs811.readData()
		accel = sensor.get_accel_data()
		sleep(0.1)
		temp = sensor.get_temp_data()

		print('eCO2: {} TVOC: {} Temp: {}\nAccel: {}'.format(eco2, tvoc, temp, accel))

		if (eco2 == 0 or accel['x'] == 0):
			i2c.write_i2c_block_data(0x68, 0x6B, [0x20])
			print('Trash value')
			continue

		json = {
			'eco2': eco2,
			'tvoc': tvoc,
			'temp': round(temp, 2),
			'accel': {
				'x': round(accel['x'], 4),
				'y': round(accel['y'], 4),
				'z': round(accel['z'], 4)
			}
		}

		response = requests.post(url = REQUEST_URL, json = json)

	except Exception as e:
		traceback.print_exc()
		print('Except: {}'.format(e))
