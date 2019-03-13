#/!bin/bash
PARAM=$1
FILE=/etc/nginx/nginx.conf
PREV=$(awk '/    server/{print $2}' $FILE)

if [ "$PARAM:6379;" = $PREV ]
then
	echo "Warning: currently already using that ip"  
else
	sed -i "s/^\(    server\)\(.*\)$/\1 $PARAM:6379;/" $FILE
	#need to run reload, possibly just the 
	/usr/sbin/nginx -s reload
fi

#mac version, regex should be the same
#sed -i "" "s/^\( *server\)\(.*\)$/\1 $PARAM/" $FILE

