# AIA Engine Java SDK Examples

## Authentication

There are two ways to authenticate the **Client**:

1. Using email/password

    ```Java
    Client client = new Client(new ApiConfig("grpc.staging.aiaengine.com:443", "<your_email>", "<your_pasword>"));
    ```

2. Using token

    ```Java
    Client client = new Client(new Client.ApiConfig("grpc.staging.aiaengine.com:443", "<token>"));
    ```

    Notes: To get/generate the authentication token, we can use the following code:

    ```Java
    // authenticate client using email and password
    Client client = new Client(new ApiConfig("grpc.staging.aiaengine.com:443", "<your_email>", "<your_pasword>"));
    // get available tokens
    ListCustomTokensResponse response = client.getUsers().listCustomTokens(ListCustomTokensRequest.newBuilder().build());
    if (response.getCustomTokensCount() > 0) {
        for (CustomToken token : response.getCustomTokensList()) {
            System.out.print("Custom token: " + token.getToken());
        }
    } else {
        // generate custom token
        CustomToken token = client.getUsers().createCustomToken(CreateCustomTokenRequest.newBuilder().build());
        System.out.print("Custom token: " + token.getToken());
    }
    ```

## How to incrementally adding new data to the existing dataset

```Java
Dataset dataset = new Dataset(client, "<dataset_id>");
dataset.appendData("data/breast_cancer_augumented_data.csv");
```

## How to run predictions

```Java
Model model = new Model(client, "<model_id>");
String result = model.predict("data/breast_cancer_prediction_data.csv", "text/csv");
System.out.print(String.format("Prediction result:\n%s", result));
```
