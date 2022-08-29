const mediaTypeApplicationJson = 'application/json';

export async function get<T>(request: RequestInfo): Promise<T> {
    const response = await fetch(request, {headers: {Accept: mediaTypeApplicationJson}});
    return await response.json();
}
