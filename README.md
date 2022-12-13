# Redis Monitor Output analyzer
Quick program to analyse results of the MONITOR command from the redis-cli

## How to use it
1. Run the Monitor command and export results on a file:
`redis-cli -h $REDIS_HOST -p $REDIS_PORT MONITOR >> $PATH`
2. Modify [Main file](./src/Main.java) putting the path of the output file ($PATH)
3. The results will be printed out in console
