# To execute multiple methods concurrently

 * `CompletableFuture` class to run asynchronous computations and then combine them.

       // Start the asynchronous computations
       CompletableFuture<Stream<FeePojo>> feeListFuture = CompletableFuture.supplyAsync(() -> new FeeDaoImpl().getFeelist());
       CompletableFuture<List<TransportPojo>> transportListFuture = CompletableFuture.supplyAsync(() -> new TransportImpl().getTransportFeeList());
       CompletableFuture<List<AccountsPojo>> accountsListFuture = CompletableFuture.supplyAsync(() -> new AccountsImpl().getAccountList());
       CompletableFuture<List<AccountsPojo>> depositeListFuture = CompletableFuture.supplyAsync(() -> new AccountsImpl().getDepositeList());
       CompletableFuture<Stream<AccountsPojo>> withdrawalListFuture = CompletableFuture.supplyAsync(() -> new AccountsImpl().withdrwalList());

        // Combine all futures using CompletableFuture.allOf()
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(
        feeListFuture,
        transportListFuture,
        accountsListFuture,
        depositeListFuture,
        withdrawalListFuture
        );
        
        // Wait for all futures to complete
        combinedFuture.join();
        
        // Retrieve the results after all futures complete // Now you can work with the lists as needed
        Stream<FeePojo> feeList = feeListFuture.get().collect(Collectors.toList());
        List<TransportPojo> transportList = transportListFuture.get();
        List<AccountsPojo> accountsList = accountsListFuture.get();
        List<AccountsPojo> depositeList = depositeListFuture.get();
        Stream<AccountsPojo> withdrawalList = withdrawalListFuture.get().collect(Collectors.toList());

 **Please note:** That CompletableFuture.get() `throws checked exceptions` that you need to handle,
 such as `InterruptedException` and `ExecutionException`. Also, ensure that the operations you’re performing in the `supplyAsync` methods are thread-safe and do not interfere with each other if they are modifying shared resources.