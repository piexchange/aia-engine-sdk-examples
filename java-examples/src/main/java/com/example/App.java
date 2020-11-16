package com.example;

import aiaengine.api.Client;
import aiaengine.api.Client.ApiConfig;
import aiaengine.api.User.CreateCustomTokenRequest;
import aiaengine.api.User.CustomToken;
import aiaengine.api.User.ListCustomTokensRequest;
import aiaengine.api.User.ListCustomTokensResponse;
import aiaengine.api.dataset.Dataset;
import aiaengine.api.model.Model;

public class App {
    public static void main(String[] args) {
        try {
            // generate token if required
            // generateToken();

            // initialise the SDK Client using token
            Client client = new Client(new Client.ApiConfig("grpc.staging.aiaengine.com:443", "<token>"));

            // addNewDataToDataset(client);

            runPrediction(client);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void generateToken() {
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
    }

    // add new data to existing dataset. This will automatically trigger the retraining process
    private static void addNewDataToDataset(Client client) throws Exception {
        Dataset dataset = new Dataset(client, "<dataset_id>");
        dataset.appendData("text/csv", "data/breast_cancer_augumented_data.csv");
    }
    
    // run prediction
    private static void runPrediction(Client client) throws Exception {
        Model model = new Model(client, "<model_id>");
        String result = model.predict("data/breast_cancer_prediction_data.csv", "text/csv");
        System.out.print(String.format("Prediction result:\n%s", result));
    }
}
