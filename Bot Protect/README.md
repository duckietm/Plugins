# Bot Protection

## Install 

Install the plugin into /plugins folder and run the emulator

## What does it do?

It will sterilize the SSO after the user has made a connection to your hotel.
This way the attacker is not able to join the next session with the SSO that was handed out.
I will update this with more protection layers later, if you have any ideas just share them and we will see how to add them !

:white_check_mark: SSO login generalized after login.

## When to use this ?

If you want to Run your emulator in debug mode and have the AUTH ticket sterilized you can use the :Bot Spam protection - plugin
But keep in mind when you run your Hotel in Debug mode off this will be done automatically, Also running an Live hotel it is advised to run this in Debug mode off on Live hotels !

How to run your hotel in Live mode:
First make sure your emulator is stopped and run the following SQL:
```sql
UPDATE emulator_settings SET value = '0' WHERE (`key` = 'debug.mode');
```
 
Then start your emulator !

How to run your hotel in DEV mode: 
First make sure your emulator is stopped and run the following SQL:
```sql
UPDATE emulator_settings SET value = '1' WHERE (`key` = 'debug.mode');
```

Now when needed you can upload the plugin to the /plugin directory
Then start your emulator ! 

## Compiled version
If you don't know how to compile there is a compiled version in /compiled