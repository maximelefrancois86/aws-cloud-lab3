# Lab Questions 

## Question 1: What type of queues can you create? State their differences?

When I try to create a queue, aws offers me the choice between Standard queue and FIFO queue.

Standard Queue:

This is the default queue type (we use this type of queue in this project).

Main characteristics :

-	High Troughput : Standard queues support a nearly unlimited number of API calls per second, per API action.

-   At least once delivery: A message is delivered at least once, but occasionally more than one copy or a message is delivered (because of the highly distributed architecture that allows nearly unlimited throughput).

-	Best effort ordering: Standard queues provide best-effort ordering which ensures that messages are generally delivered in the same order as they're sent. If the system requires that order be preserved, it is better using a FIFO (First-In-First-Out) queue or adding sequencing information in each message in order to reorder the messages when they're received.

You can use standard message queues in many scenarios, as long as your application can process messages that arrive more than once and out of order, for example:
-	Decouple live user requests from intensive background work – Let users upload media while resizing or encoding it.
-	Allocate tasks to multiple worker nodes – Process a high number of credit card validation requests.
-	Batch messages for future processing – Schedule multiple entries to be added to a database.

FIFO (First-in-first-out) Queue:

Main characteristics :

-   FIFO queues have all the capabilities of the standard queue.

-   First-In-First-Out delivery: The order in which message are sent and received is strictly preserved.

-   Limited Throughput: 300 transactions per second.

FIFO  queues are designed to enhance messaging between applications when the order of operations and events is critical, or where duplicates can't be tolerated.

Examples of situations where you might use FIFO queues include the following:

-   To make sure that user-entered commands are run in the right order.

-   To display the correct product price by sending price modifications in the right order.

-   To prevent a student from enrolling in a course before registering for an account.


Sum up of the main differences between FIFO and Standard Queue:

Message Order:

-   Standard queues provide best-effort ordering which ensures that messages are generally delivered in the same order as they are sent. Occasionally (because of the highly-distributed architecture that allows high throughput), more than one copy of a message might be delivered out of order.
-   FIFO queues offer first-in-first-out delivery and exactly-once processing: the order in which messages are sent and received is strictly preserved.

Delivery:

-   Standard queues guarantee that a message is delivered at least once and duplicates can be introduced into the queue.
-   FIFO queues ensure a message is delivered exactly once and remains available until a consumer processes and deletes it; duplicates are not introduced into the queue.

Transactions Per Second:

-   Standard queues allow nearly-unlimited number of transactions per second.
-   FIFO queues allow to process up to 3000 messages per second per API action.

Regions:

-   Standard queues are available in all the regions.
-   FIFO queues are currently available in limited regions only.

SQS Buffered Asynchronous Client:

-   FIFO queues aren’t currently compatible with the SQS Buffered Asynchronous Client, where messages are buffered at client side and send as a single request to the SQS queue to reduce cost.

Supported services:

-   Standard Queues are supported by all AWS services.
-   FIFO Queues are currently not supported by all AWS services like: CloudWatch Events, S3 Event Notifications, SNS Topic Subscriptions, Auto Scaling Lifecycle Hooks, AWS IoT Rule Actions, AWS Lambda Dead Letter Queues.

Sources:
https://us-west-2.console.aws.amazon.com/sqs/v2/home?region=us-west-2#/create-queue
https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/standard-queues.html
https://medium.com/awesome-cloud/aws-difference-between-sqs-standard-and-fifo-first-in-first-out-queues-28d1ea5e153
https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/FIFO-queues.html

_______________________________________________________________________________________________________________________________________

Question 2: In which situations is a Web-Queue-Worker architecture relevant?

According to the Windows Azure documentation, this architecture is relevant for the following cases:
    -Applications with a relatively simple domain.
    -Applications with some long-running workflows or batch operations.
    -When you want to use managed services, rather than infrastructure as a service (IaaS)
