import http from "k6/http";
import { check } from "k6";

export const options = {
    stages: [
        { duration: "10s", target: 20 },     // Warm-up
        { duration: "5s", target: 1000 },    // Sudden spike
        { duration: "20s", target: 1000 },   // Hold the spike
        { duration: "5s", target: 20 },      // Drop suddenly
        { duration: "10s", target: 20 },     // Recovery
    ],
};

export default function () {

    const response = http.get("http://localhost:8080/users");

    check(response, {
        "Status is 200": (r) => r.status === 200,
    });

}