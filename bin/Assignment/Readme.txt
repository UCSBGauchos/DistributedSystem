The main function is Userinterface.java, just run this file

In my code, I don't use regular expression to parse the input command. It's just a static parse which uses subString function. 
Please Note: DONT INPUT SPACE IN YOUR INPUT COMMAND

The given test case and the result:
Increment(1,“X”)

getValue(1,”X”)
1

getValue(2,”X”)
null

PrintState(1)
Time table is
1 0 0 
0 0 0 
0 0 0 
log is increnemt(X) 

SendLog(1,2)
TransID is 1
add this message to the message list.......

Increment(1, “Y”)

PrintState(2)
Time table is
0 0 0 
0 0 0 
0 0 0 
nothing in the log

ReceiveLog(1)

PrintState(2)
Time table is
1 0 0 
1 0 0 
0 0 0 
log is increnemt(X) 

getValue(2,”X”)
1

