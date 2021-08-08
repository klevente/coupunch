# Company Analytics

For better understanding of their business and progress, companies can track various indicators through the various reports, which is provided by a 3rd party BI solution.

## Architecture

The analytics stack for each company is detailed below:

1. Data Lake: database storing historical information about the company, from which users can query and visualize data
2. Data Loader: a component which updates the data lake with new information
    * This could be a Spring application that gets called by other services using the message queue to add a piece of data when something happens
    * Alternatively, it could also be a scheduled process that updates the lake periodically from the production database
3. Report Engine: a self-hosted BI application capable of creating visualizations and data queries easily, which can be integrated into the company's website somehow