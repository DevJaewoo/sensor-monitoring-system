import requests

import smbus
from time import sleep
from CCS811 import CCS811
from MPU6050 import MPU6050

SERVER_IP = '192.168.166.26'
SERVER_PORT = 8080
CLIENT_ID = 1
REQUEST_URL = 'http://{0}:{1}/api/{2}/sensor'.format(SERVER_IP, SERVER_PORT, CLIENT_ID)

i2c = smbus.SMBus(1)

ccs811 = CCS811(i2c)
sensor = MPU6050(i2c)

sleep(0.1)

while True:

	sleep(1)

	try:
		[eco2, tvoc] = ccs811.readData()
		accel = sensor.get_accel_data()
		temp = sensor.get_temp_data()

		if (eco2 == 0 or accel['x'] == 0):
			print('Trash value')
			continue

		print('eCO2: {} TVOC: {} Temp: {}\nAccel: {}'.format(eco2, tvoc, temp, accel))

		json = {
			'eco2': eco2,
			'tvoc': tvoc,
			'temp': round(temp, 2),
			'accel': {
				'x': round(accel['x'], 2),
				'y': round(accel['y'], 2),
				'z': round(accel['z'], 2)
			}
		}

		response = requests.post(url = REQUEST_URL, json = json)

	except Exception as e:
		print('Except: {}'.format(e))
