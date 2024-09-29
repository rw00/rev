Implement a Load Balancer, which is a database of service instances and their locations.
The service registry needs to be updated each time a new service comes online.

Build a fake Load Balancer. No network calls should be made.
Data should be stored in-memory instead of a real database.

- It should be possible to register an instance, identified by an address.
- It should not be possible to register the same address more than once.
- Load Balancer should contain up to 10 instances.

Develop an algorithm that, when invoking the Load Balancer 's get() method multiple times,
should return one backend-instance choosing between the registered ones randomly.
