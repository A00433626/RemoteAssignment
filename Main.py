import sys
import logging

#is a Number or not
def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        pass
 
    try:
        import unicodedata
        unicodedata.numeric(s)
        return True
    except (TypeError, ValueError):
        pass


def billsplitter():

    inputFile = sys.argv[1]
    data = open(inputFile,encoding='utf-8-sig')
    filelist1 = data.readlines()
    #Global Index
    index=0
    #Intitial total
    total = 0
    outputFile = inputFile+".out"
    outdata = open(outputFile, "w")
    sys.stdout = open(outputFile, "w")
    totalFileListSize=len(filelist1)
    #print(totalFileListSize)
    if(float(filelist1[totalFileListSize-1])==0):
        for item in filelist1:
            check = filelist1[index]
            integer=is_number(check)
            #print(integer)
            if integer==False and check.isalnum()==False:
                logging.error("Entered Number Should be a Number")
                break
            elif check.isalnum()==True and integer==False:
                logging.error("Entered Number Should be a AlphaNumeric")
                break
            elif float(check) < 0:
                logging.error("Entered Number Should not be a Positive")
                break
            elif check=='0':
                break
            numberofpersons =int(filelist1[index])
            index = index+1
            #Stores The Group Total
            total=0
            #records Temporary PersonCount
            personCount=numberofpersons
            individualTotalExpense=[]
            #Iterating Over the Total Number of Persons in a Group
            while numberofpersons!=0:
                numberofitems=float(filelist1[index])
                current_total=0
                #Expenses made by Each Person
                while numberofitems!=0:
                    index = index+1
                    #Group Total
                    total = total+float(filelist1[index])
                    #Individual Expenses Sum
                    current_total = current_total+float(filelist1[index])
                    numberofitems=numberofitems-1
                    #Appending Group Individual Sum in a  List
                individualTotalExpense.append(current_total)
                numberofpersons=numberofpersons-1
                index=index+1
            if personCount!=0:    
                total=total/personCount
            #Iterating Over The individualExpense List and Finding the Lent/Owe Amount
            for currentTotal in individualTotalExpense:
                if total-currentTotal >0:
                    output = '(' + '$' + str(round(total-currentTotal,3)) + ')'
                    print(output, sys.stdout)
                else:
                    output = '$' + str(-round(total-currentTotal,3))
                    print(output, sys.stdout)
            print("\n")
        data.close()
        #Write to OutFile
        outdata.close()

#Function Starts 
billsplitter()