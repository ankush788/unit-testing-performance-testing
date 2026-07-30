import http from "k6/http";
import { check } from "k6";

export const options = {
    vus: 2,
    duration: "30s",
};

export default function () {

    const response = http.get("http://localhost:8080/users");

    check(response, {
        "status is 200": (r) => r.status === 200,
        "response < 500ms": (r) => r.timings.duration < 500,
    });
}