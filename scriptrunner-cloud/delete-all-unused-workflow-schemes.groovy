/**
 * delete-all-unused-workflow-schemes.groovy
 *
 * Delete all the Workflow Schemes that don't have any Workflows Attached to
 * them.
 *
 * These scripts are intended to be used within the ScriptRunner plugin within
 * Jira Cloud.  https://marketplace.atlassian.com/apps/6820 
 */


// Get a list of the first 200 Workflow Schemes
Map<String, Object> searchResult = get('/rest/api/3/workflowscheme')
    .queryString("maxResults", "200")
    .asObject(Map)
    .body
    
def ws = (List<Map<String, Object>>) searchResult.values

// Iterate over all the Workflow Schemes
ws.each {
    // Only want the Workflow Schemes without any mappings
    if (it.issueTypeMappings.size() == 0) { 
        println "Deleting: ${it.id} - ${it.name}"
        def hr = delete('/rest/api/3/workflowscheme/'+ it.id).asString()
        println hr
    }
}
