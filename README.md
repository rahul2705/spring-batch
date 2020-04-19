### Spring batch

#### Summary
This repo illustrates the working of spring-batch framework.

Spring batch framework is used to process large volumes of records. It achieves
this by dividing the overall process in 3 parts.
* A **reader** that reads the data from a source (can be any file or a database)
* A **processor** that transforms the data received from reader.
* A **writer** that writes the tansformed data to a destination (can be file or a database)

To further optimise the processing, it supports multithreading that performs parallel reads and writes.

#### Use case
This repo reads employee data (First Name and Last Name) from a csv file.
 
The processor then generates an employee id for each record and then each record is inserted to a database in chunks of 100.

This also uses a thread pool of size 5 to perform parallel reads and writes.

