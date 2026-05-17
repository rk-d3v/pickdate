package com.pickdate.observability.audit.infrastructure;


record AuditApiConst() {

    public static final String AUDIT_EXAMPLE_JSON = """
            {
              "id": "a1b2c3d4-1111-2222-3333-444455556666",
              "action": "login_success_event",
              "user": "b5c6d7e8-2222-3333-4444-555566667777",
              "remoteAddress": "192.168.0.10",
              "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
              "payload": null,
              "createdAt": "2025-12-25T12:00:00Z"
            }
            """;

    public static final String AUDITS_EXAMPLE_JSON = """
            {
              "content": [
                {
                  "id": "a1b2c3d4-1111-2222-3333-444455556666",
                  "action": "login_success_event",
                  "user": "b5c6d7e8-2222-3333-4444-555566667777",
                  "remoteAddress": "192.168.0.10",
                  "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
                  "payload": null,
                  "createdAt": "2025-12-25T12:00:00Z"
                },
                {
                  "id": "e7f8g9h0-7777-8888-9999-000011112222",
                  "action": "login_failed_event",
                  "user": "c9d0e1f2-3333-4444-5555-666677778888",
                  "remoteAddress": "192.168.0.20",
                  "userAgent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)",
                  "payload": null,
                  "createdAt": "2025-12-24T08:30:00Z"
                }
              ],
              "pageable": {
                "sort": { "sorted": true, "unsorted": false, "empty": false },
                "pageNumber": 0,
                "pageSize": 20,
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "totalElements": 2,
              "totalPages": 1,
              "last": true,
              "size": 20,
              "number": 0,
              "sort": { "sorted": true, "unsorted": false, "empty": false },
              "first": true,
              "numberOfElements": 2,
              "empty": false
            }
            """;
}
