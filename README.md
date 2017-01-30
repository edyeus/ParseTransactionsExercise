**Parse Transactions Exercise**

_Compile_
This is a maven project. Please use mvn package to build the project. Find the executable jar in the target folder.
There is one pre-packaged jar in the project root folder. Please feel free to use this one.

_Run_
The project require java 1.8 environment.

Simply run java -jar theCreatedJarName.jar to run default user with no filter option.

To use filter options, a script is needed. And example script is included in the project root folder.
To run app with script, please use: java -jar theCreatedJarName.jar -s=pathToScript. For example, if the script is named script and in the same folder with the jar file, simple run java -jar theCreatedJarName.jar -s=scipt

_Script Syntax_
Each line in the script represent one report for one user. The script can take six parameters:
    -u, user id
    -t, token
    -a, api token
    -d, ignore donuts related transactions
    -c, crystal ball, report include predicated transactions
    -cc, ignore credit card related transactions
-u, -t, and -a have to be used together in order to use a different user, otherwise it will toggle back to default user. If no user options are specified, the default user will be used.
-d, -c, and -cc can be used alone or together.

Example valid script:
    -defaultUser -allInvalidOptionsAreIgnoredAnyWay
    -u=1110590645 -t=BFBC0D8DAAA0C9F1AB570531A4649D07 -a=AppTokenForInterview
    -d
    -cc
    -c
    -d -cc -c
    
_Result_
Log is set at info level and output to console. This includes logs indicating step in the process as well as resulting reports.
The app will generate a "result" file containing all spending and income reports. Different reports will be separated by line.
If -cc option is used, a file named "ccs" will be created, including filtered out credit card related transactions.

_Please feel free to contact me if you have any questions, email: edy.buongiorno@gmail.com_
