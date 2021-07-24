/**
 * delete-all-workflows.groovy
 *
 * Attempt to delete all the Workflows and hope that Jira's DELETE endpoint
 * won't delete any that are in use.
 *
 * These scripts are intended to be used within the ScriptRunner plugin within
 * Jira Cloud.  https://marketplace.atlassian.com/apps/6820 
 */


// Get a list of the first N Workflows (appears that N is max of 50)
Map<String, Object> searchResult = get('/rest/api/3/workflow/search')
    .queryString("maxResults", "200")
    .asObject(Map)
    .body
    
def w = (List<Map<String, Object>>) searchResult.values


// Iterate over all the Workflows; Attempt to delete everyone :eek:
w.each {
    if (it.id.entityId != null) {
        println "Attempting to delete: ${it.id.entityId} - ${it.id.name}"
        def hr = delete('/rest/api/3/workflow/'+ it.id.entityId).asString()
        println hr
    }
}